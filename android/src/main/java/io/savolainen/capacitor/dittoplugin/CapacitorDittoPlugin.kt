package io.savolainen.capacitor.dittoplugin

import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import live.ditto.DittoDocument
import live.ditto.DittoError
import live.ditto.DittoLiveQuery
import live.ditto.DittoLiveQueryCallback
import live.ditto.DittoLiveQueryEvent
import live.ditto.DittoSubscription
import org.json.JSONArray
import org.json.JSONObject
import java.security.InvalidParameterException

private data class LiveQueryInvocation(val call: PluginCall, val liveQuery: DittoLiveQuery)

// From StackOverflow: https://stackoverflow.com/questions/44870961/how-to-map-a-json-string-to-kotlin-map
fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
    when (val value = this[it]) {
        is JSONArray -> {
            val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
            JSONObject(map).toMap().values.toList()
        }

        is JSONObject -> value.toMap()
        JSONObject.NULL -> null
        else -> value
    }
}

@CapacitorPlugin(name = "CapacitorDitto")
class CapacitorDittoPlugin : Plugin() {
    private val implementation = CapacitorDitto()
    private val subscriptions = HashMap<UInt, DittoSubscription>()
    private val liveQueries = HashMap<String, LiveQueryInvocation>()
    private var subscriptionId = 0u

    @PluginMethod
    fun init(call: PluginCall) {
        val identity = call.getObject("identity", null)

        if (identity == null) {
            call.reject("No identity given")
            return
        }

        try {
            implementation.init(context, identity)
            call.resolve()
        } catch (e: DittoError) {
            Log.e("Ditto error", e.message!!)
            call.reject(e.message!!)
        } catch (e: InvalidParameterException) {
            Log.e("Invalid identity object", e.message!!)
            call.reject(e.message!!)
        }
    }

    @PluginMethod
    fun isInitialized(call: PluginCall) {
        val isInitialized = implementation.isInitialized()
        call.resolve(JSObject().put("isInitialized", isInitialized))
    }

    @PluginMethod
    fun startSync(call: PluginCall) {
        try {
            implementation.startSync()
            call.resolve()
        } catch (e: NoDittoError) {
            call.reject(e.message)
        }
    }

    @PluginMethod
    fun stopSync(call: PluginCall) {
        try {
            implementation.stopSync()
            call.resolve()
        } catch (e: NoDittoError) {
            call.reject(e.message)
        }
    }

    @PluginMethod
    fun upsert(call: PluginCall) {
        val collectionName = call.getString("collectionName", null)
        val data = call.getObject("data", null)

        if (collectionName == null) {
            call.reject("No collection name given")
            return
        }

        if (data == null) {
            call.reject("No data given")
            return
        }

        try {
            val documentId = implementation.upsert(collectionName, data.toMap())
            val ret = JSObject()
            ret.put("documentId", documentId)
            call.resolve(ret)
        } catch (e: NoDittoError) {
            call.reject(e.message)
        }
    }

    @PluginMethod
    fun subscribe(call: PluginCall) {
        val collectionName = call.getString("collectionName", null)
        val queryString = call.getString("queryString", null) ?: "true"
        val queryArgs = call.getObject("queryArgs", null)

        if (collectionName == null) {
            call.reject("No collection name given")
            return
        }

        val subscription: DittoSubscription

        try {
            val queryArgsMap: Map<String, Any?> = queryArgs?.toMap() ?: HashMap()

            subscription = implementation.subscribe(collectionName, queryString, queryArgsMap)
        } catch (e: NoDittoError) {
            call.reject(e.message)
            return
        }

        subscriptions[subscriptionId] = subscription
        val ret = JSObject()
        ret.put("subscriptionId", subscriptionId.toString())
        subscriptionId++
        call.resolve(ret)
    }

    @PluginMethod
    fun cancelSubscription(call: PluginCall) {
        val subscriptionId = call.getString("subscriptionId", null)

        if (subscriptionId == null) {
            call.reject("No subscription ID given")
            return
        }

        val subscription = subscriptions[subscriptionId.toUInt()]

        if (subscription == null) {
            call.reject("No subscription found with ID $subscriptionId")
            return
        }

        subscription.close()

        call.resolve()
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    fun startLiveQuery(call: PluginCall) {
        call.setKeepAlive(true)
        val collectionName = call.getString("collectionName", null)
        val queryString = call.getString("queryString", null) ?: "true"
        val queryArgs = call.getObject("queryArgs", null)

        if (collectionName == null) {
            call.reject("No collection name given")
            return
        }

        val liveQuery: DittoLiveQuery

        try {
            val queryArgsMap: Map<String, Any?> = queryArgs?.toMap() ?: HashMap()

            liveQuery = implementation.startLiveQuery(collectionName, queryString, object : DittoLiveQueryCallback {
                override fun update(docs: List<DittoDocument>, event: DittoLiveQueryEvent) {
                    val ret = JSObject()
                    val mappedDocs = docs.map { JSObject.fromJSONObject(JSONObject(it.value)) }
                    ret.put("docs", JSONArray(mappedDocs))
                    call.resolve(ret)
                }
            }, queryArgsMap)
        } catch (e: NoDittoError) {
            call.reject(e.message)
            return
        }

        liveQueries[call.callbackId] = LiveQueryInvocation(call, liveQuery)
    }

    @PluginMethod
    fun stopLiveQuery(call: PluginCall) {
        val liveQueryId = call.getString("id", null)

        if (liveQueryId == null) {
            call.reject("No live query ID given")
            return
        }

        val liveQuery = liveQueries[liveQueryId]

        if (liveQuery == null) {
            call.reject("No live query found with ID $liveQueryId")
            return
        }

        liveQuery.liveQuery.close()
        liveQuery.call.setKeepAlive(false)
        liveQuery.call.release(bridge)
        liveQueries.remove(liveQueryId)

        call.resolve()
    }

    @PluginMethod
    fun checkDittoPermissions(call: PluginCall) {
        try {
            implementation.checkDittoPermissions(activity)
            call.resolve()
        } catch (e: Error) {
            Log.e("CapacitorDitto", "Could not check for permissions: ${e.message!!}")
            call.reject(e.message!!)
        }
    }
}
