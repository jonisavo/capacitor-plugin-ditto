package io.savolainen.capacitor.dittoplugin

import android.app.Activity
import android.content.Context
import android.util.Log
import com.getcapacitor.JSObject
import live.ditto.Ditto
import live.ditto.DittoDependencies
import live.ditto.android.DefaultAndroidDittoDependencies
import live.ditto.DittoIdentity
import live.ditto.DittoLiveQuery
import live.ditto.DittoLiveQueryCallback
import live.ditto.DittoLogger
import live.ditto.DittoLogLevel
import live.ditto.DittoSubscription
import live.ditto.transports.DittoSyncPermissions
import java.security.InvalidParameterException

const val ONLINE_PLAYGROUND = "onlinePlayground"

class NoDittoError : Error() {
    override val message: String
        get() = "Ditto is not initialized"
}

class CapacitorDitto {
    private var TAG = "CapacitorDitto"
    private var ditto: Ditto? = null

    private fun parseIdentity(deps: DittoDependencies, obj: JSObject): DittoIdentity? {
        val type = obj.getString("type", null) ?: return null

        return when (type) {
            ONLINE_PLAYGROUND -> {
                val appId = obj.getString("appID", null) ?: return null
                val token = obj.getString("token", null) ?: return null
                val enableDittoCloudSync = obj.getBoolean("enableDittoCloudSync", true) ?: true
                val customAuthUrl = obj.getString("customAuthUrl", null)

                return DittoIdentity.OnlinePlayground(
                        deps,
                        appId = appId,
                        token = token,
                        enableDittoCloudSync = enableDittoCloudSync,
                        customAuthUrl = customAuthUrl
                )
            }

            else -> null
        }
    }

    fun init(context: Context, identityObj: JSObject) {
        val androidDependencies = DefaultAndroidDittoDependencies(context)
        val identity = parseIdentity(androidDependencies, identityObj)
                ?: throw InvalidParameterException("Could not parse identity")

        DittoLogger.minimumLogLevel = DittoLogLevel.DEBUG
        ditto = Ditto(androidDependencies, identity)
    }

    fun isInitialized(): Boolean {
        return ditto != null
    }

    fun startSync() {
        if (ditto == null)
            throw NoDittoError()

        ditto!!.startSync()
    }

    fun stopSync() {
        if (ditto == null)
            throw NoDittoError()

        ditto!!.stopSync()
    }

    fun upsert(collectionName: String, data: Map<String, Any?>): String {
        if (ditto == null)
            throw NoDittoError()

        val collection = ditto!!.store.collection(collectionName)

        val documentId = collection.upsert(data)

        return documentId.toString()
    }

    fun subscribe(collectionName: String, queryString: String, queryArgs: Map<String, Any?>?): DittoSubscription {
        if (ditto == null)
            throw NoDittoError()

        val collection = ditto!!.store.collection(collectionName)

        return collection.find(queryString, queryArgs ?: HashMap()).subscribe()
    }

    fun startLiveQuery(collectionName: String, queryString: String, cb: DittoLiveQueryCallback, queryArgs: Map<String, Any?>?): DittoLiveQuery {
        if (ditto == null)
            throw NoDittoError()

        val collection = ditto!!.store.collection(collectionName)

        return collection.find(queryString, queryArgs ?: HashMap()).observeLocal(cb)
    }

    fun checkDittoPermissions(activity: Activity) {
        Log.d(TAG, "Checking Ditto permissions")
        val missing = DittoSyncPermissions(activity).missingPermissions()
        Log.d(TAG, "Missing permissions: ${missing.joinToString(", ")}")
        if (missing.isNotEmpty()) {
            Log.d(TAG, "Requesting permissions...")
            activity.requestPermissions(missing, 0)
        }
    }
}