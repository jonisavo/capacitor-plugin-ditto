# @jonisavo/capacitor-plugin-ditto

Use Ditto's native APIs inside your Capacitor app.

## Install

```bash
npm install @jonisavo/capacitor-plugin-ditto
npx cap sync
```

## API

<docgen-index>

* [`init(...)`](#init)
* [`isInitialized()`](#isinitialized)
* [`checkDittoPermissions()`](#checkdittopermissions)
* [`startSync()`](#startsync)
* [`stopSync()`](#stopsync)
* [`upsert(...)`](#upsert)
* [`subscribe(...)`](#subscribe)
* [`cancelSubscription(...)`](#cancelsubscription)
* [`startLiveQuery(...)`](#startlivequery)
* [`stopLiveQuery(...)`](#stoplivequery)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### init(...)

```typescript
init(options?: InitOptions | undefined) => Promise<void>
```

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#initoptions">InitOptions</a></code> |

--------------------


### isInitialized()

```typescript
isInitialized() => Promise<{ isInitialized: boolean; }>
```

**Returns:** <code>Promise&lt;{ isInitialized: boolean; }&gt;</code>

--------------------


### checkDittoPermissions()

```typescript
checkDittoPermissions() => Promise<void>
```

--------------------


### startSync()

```typescript
startSync() => Promise<void>
```

--------------------


### stopSync()

```typescript
stopSync() => Promise<void>
```

--------------------


### upsert(...)

```typescript
upsert(options: UpsertOptions) => Promise<{ documentId: string; }>
```

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#upsertoptions">UpsertOptions</a></code> |

**Returns:** <code>Promise&lt;{ documentId: string; }&gt;</code>

--------------------


### subscribe(...)

```typescript
subscribe(options: SubscribeOptions) => Promise<{ subscriptionId: string; }>
```

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#subscribeoptions">SubscribeOptions</a></code> |

**Returns:** <code>Promise&lt;{ subscriptionId: string; }&gt;</code>

--------------------


### cancelSubscription(...)

```typescript
cancelSubscription(options: CancelSubscriptionOptions) => Promise<void>
```

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#cancelsubscriptionoptions">CancelSubscriptionOptions</a></code> |

--------------------


### startLiveQuery(...)

```typescript
startLiveQuery<T>(options: StartLiveQueryOptions, callback: ObserveCallback<T>) => Promise<CallbackId>
```

| Param          | Type                                                                    |
| -------------- | ----------------------------------------------------------------------- |
| **`options`**  | <code><a href="#startlivequeryoptions">StartLiveQueryOptions</a></code> |
| **`callback`** | <code><a href="#observecallback">ObserveCallback</a>&lt;T&gt;</code>    |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### stopLiveQuery(...)

```typescript
stopLiveQuery(options: StopLiveQueryOptions) => Promise<void>
```

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#stoplivequeryoptions">StopLiveQueryOptions</a></code> |

--------------------


### Interfaces


#### InitOptions

| Prop           | Type                                          |
| -------------- | --------------------------------------------- |
| **`web`**      | <code>WebInitOptions</code>                   |
| **`identity`** | <code><a href="#identity">Identity</a></code> |


#### IdentityOfflinePlayground

An identity to be used while in development when you want to control the app
name and the site ID of the peer.

| Prop         | Type                                                | Description                                                                                                                                                                                                                                                                                                |
| ------------ | --------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`type`**   | <code>'offlinePlayground'</code>                    |                                                                                                                                                                                                                                                                                                            |
| **`appID`**  | <code>string</code>                                 | The app ID for the Ditto instance.                                                                                                                                                                                                                                                                         |
| **`siteID`** | <code>number \| <a href="#bigint">BigInt</a></code> | The `siteID` needs to be an unsigned 64 bit integer &gt;= 0, i.e. either a regular non-fractional `number` or a <a href="#bigint">`BigInt`</a> in the range between 0 and 2^64 - 1 (inclusive). If `siteID` is `0`, Ditto will generate an appropriate site ID and persist it when needed. Default is `0`. |


#### BigInt

| Prop                       | Type                                        |
| -------------------------- | ------------------------------------------- |
| **`[Symbol.toStringTag]`** | <code>'<a href="#bigint">BigInt</a>'</code> |

| Method             | Signature                                                                                                                                   | Description                                                                           |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------- |
| **toString**       | (radix?: number \| undefined) =&gt; string                                                                                                  | Returns a string representation of an object.                                         |
| **toLocaleString** | (locales?: string \| undefined, options?: <a href="#biginttolocalestringoptions">BigIntToLocaleStringOptions</a> \| undefined) =&gt; string | Returns a string representation appropriate to the host environment's current locale. |
| **valueOf**        | () =&gt; bigint                                                                                                                             | Returns the primitive value of the specified object.                                  |


#### BigIntToLocaleStringOptions

| Prop                           | Type                                                                                                                           | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| ------------------------------ | ------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`localeMatcher`**            | <code>string</code>                                                                                                            | The locale matching algorithm to use.The default is "best fit". For information about this option, see the {@link https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl#Locale_negotiation Intl page}.                                                                                                                                                                                                                                                                                                |
| **`style`**                    | <code>string</code>                                                                                                            | The formatting style to use , the default is "decimal".                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| **`numberingSystem`**          | <code>string</code>                                                                                                            |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| **`unit`**                     | <code>string</code>                                                                                                            | The unit to use in unit formatting, Possible values are core unit identifiers, defined in UTS #35, Part 2, Section 6. A subset of units from the full list was selected for use in ECMAScript. Pairs of simple units can be concatenated with "-per-" to make a compound unit. There is no default value; if the style is "unit", the unit property must be provided.                                                                                                                                                                 |
| **`unitDisplay`**              | <code>string</code>                                                                                                            | The unit formatting style to use in unit formatting, the defaults is "short".                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| **`currency`**                 | <code>string</code>                                                                                                            | The currency to use in currency formatting. Possible values are the ISO 4217 currency codes, such as "USD" for the US dollar, "EUR" for the euro, or "CNY" for the Chinese RMB — see the Current currency & funds code list. There is no default value; if the style is "currency", the currency property must be provided. It is only used when [[Style]] has the value "currency".                                                                                                                                                  |
| **`currencyDisplay`**          | <code>string</code>                                                                                                            | How to display the currency in currency formatting. It is only used when [[Style]] has the value "currency". The default is "symbol". "symbol" to use a localized currency symbol such as €, "code" to use the ISO currency code, "name" to use a localized currency name such as "dollar"                                                                                                                                                                                                                                            |
| **`useGrouping`**              | <code>boolean</code>                                                                                                           | Whether to use grouping separators, such as thousands separators or thousand/lakh/crore separators. The default is true.                                                                                                                                                                                                                                                                                                                                                                                                              |
| **`minimumIntegerDigits`**     | <code>1 \| 2 \| 10 \| 9 \| 8 \| 7 \| 6 \| 5 \| 4 \| 3 \| 11 \| 12 \| 13 \| 14 \| 15 \| 16 \| 17 \| 18 \| 19 \| 20 \| 21</code> | The minimum number of integer digits to use. Possible values are from 1 to 21; the default is 1.                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| **`minimumFractionDigits`**    | <code>0 \| 1 \| 2 \| 10 \| 9 \| 8 \| 7 \| 6 \| 5 \| 4 \| 3 \| 11 \| 12 \| 13 \| 14 \| 15 \| 16 \| 17 \| 18 \| 19 \| 20</code>  | The minimum number of fraction digits to use. Possible values are from 0 to 20; the default for plain number and percent formatting is 0; the default for currency formatting is the number of minor unit digits provided by the {@link http://www.currency-iso.org/en/home/tables/table-a1.html ISO 4217 currency codes list} (2 if the list doesn't provide that information).                                                                                                                                                      |
| **`maximumFractionDigits`**    | <code>0 \| 1 \| 2 \| 10 \| 9 \| 8 \| 7 \| 6 \| 5 \| 4 \| 3 \| 11 \| 12 \| 13 \| 14 \| 15 \| 16 \| 17 \| 18 \| 19 \| 20</code>  | The maximum number of fraction digits to use. Possible values are from 0 to 20; the default for plain number formatting is the larger of minimumFractionDigits and 3; the default for currency formatting is the larger of minimumFractionDigits and the number of minor unit digits provided by the {@link http://www.currency-iso.org/en/home/tables/table-a1.html ISO 4217 currency codes list} (2 if the list doesn't provide that information); the default for percent formatting is the larger of minimumFractionDigits and 0. |
| **`minimumSignificantDigits`** | <code>1 \| 2 \| 10 \| 9 \| 8 \| 7 \| 6 \| 5 \| 4 \| 3 \| 11 \| 12 \| 13 \| 14 \| 15 \| 16 \| 17 \| 18 \| 19 \| 20 \| 21</code> | The minimum number of significant digits to use. Possible values are from 1 to 21; the default is 1.                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| **`maximumSignificantDigits`** | <code>1 \| 2 \| 10 \| 9 \| 8 \| 7 \| 6 \| 5 \| 4 \| 3 \| 11 \| 12 \| 13 \| 14 \| 15 \| 16 \| 17 \| 18 \| 19 \| 20 \| 21</code> | The maximum number of significant digits to use. Possible values are from 1 to 21; the default is 21.                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| **`notation`**                 | <code>string</code>                                                                                                            | The formatting that should be displayed for the number, the defaults is "standard" "standard" plain number formatting "scientific" return the order-of-magnitude for formatted number. "engineering" return the exponent of ten when divisible by three "compact" string representing exponent, defaults is using the "short" form                                                                                                                                                                                                    |
| **`compactDisplay`**           | <code>string</code>                                                                                                            | used only when notation is "compact"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |


#### IdentitySharedKey

An identity with intermediate level of security where all users and devices
are trusted and a single shared secret (key) between all peers satisfies the
security requirements.

**NOTE**: This identity type is only supported for Node environments, using
this to create a Ditto instance in the web browser will throw an exception.

| Prop            | Type                                                | Description                                                                                                                                                                                                                                                                                                |
| --------------- | --------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`type`**      | <code>'sharedKey'</code>                            |                                                                                                                                                                                                                                                                                                            |
| **`appID`**     | <code>string</code>                                 | The app ID for the Ditto instance.                                                                                                                                                                                                                                                                         |
| **`siteID`**    | <code>number \| <a href="#bigint">BigInt</a></code> | The `siteID` needs to be an unsigned 64 bit integer &gt;= 0, i.e. either a regular non-fractional `number` or a <a href="#bigint">`BigInt`</a> in the range between 0 and 2^64 - 1 (inclusive). If `siteID` is `0`, Ditto will generate an appropriate site ID and persist it when needed. Default is `0`. |
| **`sharedKey`** | <code>string</code>                                 | A base64 encoded DER representation of a private key, which is shared between devices for a single app.                                                                                                                                                                                                    |


#### IdentityManual

A manually-provided certificate identity. This accepts a
base64-encoded bundle.

A manual identity's `appID` is encoded in its certificate.

| Prop              | Type                  |
| ----------------- | --------------------- |
| **`type`**        | <code>'manual'</code> |
| **`certificate`** | <code>string</code>   |


#### IdentityOnlinePlayground

Test a Ditto Cloud app with weak shared token authentication ("Playground
mode"). This mode is not secure and must only be used for development.

| Prop                       | Type                            | Description                                                                                                                             |
| -------------------------- | ------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------- |
| **`type`**                 | <code>'onlinePlayground'</code> |                                                                                                                                         |
| **`appID`**                | <code>string</code>             | An ID identifying this app registration on the Ditto portal, which can be found at https://portal.ditto.live.                           |
| **`token`**                | <code>string</code>             | A shared token used to set up the OnlinePlayground session. This token is provided by the Ditto Portal when setting up the application. |
| **`enableDittoCloudSync`** | <code>boolean</code>            | If true, auto-configure sync with Ditto Cloud. Default is `true`.                                                                       |
| **`customAuthURL`**        | <code>string</code>             | If specified, use a custom authentication service instead of Ditto Cloud.                                                               |
| **`customDittoCloudURL`**  | <code>string</code>             | A custom Ditto Cloud URL.                                                                                                               |


#### IdentityOnlineWithAuthentication

Run Ditto in secure production mode, logging on to Ditto Cloud or an
on-premises authentication server. User permissions are centrally managed.
Sync will not work until a successful login has occurred.

The only required configuration is the application's UUID, which can be found
on the Ditto portal where the app is registered.

By default cloud sync is enabled. This means the SDK will sync to a Big Peer
in Ditto's cloud when an internet connection is available. This is controlled
by the `enableCloudSync` parameter. If `true` (default), a suitable wss://
URL will be added to the `TransportConfig`. To prevent cloud sync, or to
specify your own URL later, pass `false`.

Authentication requests are handled by Ditto's cloud by default, configured
in the portal at https://portal.ditto.live.

To use a different or on-premises authentication service, pass a custom HTTPS
base URL as the `customAuthUrl` parameter.

| Prop                       | Type                                                                    | Description                                                                                                   |
| -------------------------- | ----------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| **`type`**                 | <code>'onlineWithAuthentication'</code>                                 |                                                                                                               |
| **`appID`**                | <code>string</code>                                                     | An ID identifying this app registration on the Ditto portal, which can be found at https://portal.ditto.live. |
| **`enableDittoCloudSync`** | <code>boolean</code>                                                    | If true, auto-configure sync with Ditto Cloud. Default is `true`.                                             |
| **`authHandler`**          | <code><a href="#authenticationhandler">AuthenticationHandler</a></code> | A handler for when Ditto requires (re)authentication.                                                         |
| **`customAuthURL`**        | <code>string</code>                                                     | If specified, use a custom authentication service instead of Ditto Cloud.                                     |
| **`customDittoCloudURL`**  | <code>string</code>                                                     | A custom Ditto Cloud URL.                                                                                     |


#### AuthenticationHandler

Provides feedback to the developer about Ditto authentication status.

| Prop                                | Type                                                                             | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| ----------------------------------- | -------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`authenticationRequired`**        | <code>(authenticator: Authenticator) =&gt; void</code>                           | There is no Ditto authentication token or it has expired. Sync will not work until there is a successful login using one of the login methods on {@link Authenticator}.                                                                                                                                                                                                                                                                                                                                                                                  |
| **`authenticationExpiringSoon`**    | <code>(authenticator: Authenticator, secondsRemaining: number) =&gt; void</code> | Warns that the Ditto authentication token is getting old. Ditto will attempt to refresh tokens periodically, starting from halfway through the token's validity period. This reduces the risk of authentication expiring while the user is offline. The refresh will happen automatically if Ditto has a suitable refresh token. If new credentials are required, such as a third-party token or a username/password, then Ditto does not cache that information and you must submit it again using one of the `login` methods on {@link Authenticator}. |
| **`authenticationStatusDidChange`** | <code>((authenticator: Authenticator) =&gt; void)</code>                         | Notifies the authentication handler that the authentication status did change. Use the `authenticator`s property `status` to query for the current authentication status. This method is **optional**.                                                                                                                                                                                                                                                                                                                                                   |


#### UpsertOptions

| Prop                 | Type                                                    |
| -------------------- | ------------------------------------------------------- |
| **`collectionName`** | <code>string</code>                                     |
| **`data`**           | <code><a href="#documentvalue">DocumentValue</a></code> |


#### SubscribeOptions

| Prop                 | Type                                                      |
| -------------------- | --------------------------------------------------------- |
| **`collectionName`** | <code>string</code>                                       |
| **`queryString`**    | <code>string</code>                                       |
| **`queryArgs`**      | <code><a href="#queryarguments">QueryArguments</a></code> |
| **`limit`**          | <code>number</code>                                       |


#### CancelSubscriptionOptions

| Prop                 | Type                |
| -------------------- | ------------------- |
| **`subscriptionId`** | <code>string</code> |


#### StartLiveQueryOptions

| Prop                 | Type                                                                                               |
| -------------------- | -------------------------------------------------------------------------------------------------- |
| **`collectionName`** | <code>string</code>                                                                                |
| **`queryString`**    | <code>string</code>                                                                                |
| **`queryArgs`**      | <code><a href="#queryarguments">QueryArguments</a></code>                                          |
| **`limit`**          | <code>number</code>                                                                                |
| **`sort`**           | <code>{ propertyPath: string; sortDirection?: <a href="#sortdirection">SortDirection</a>; }</code> |


#### StopLiveQueryOptions

| Prop     | Type                |
| -------- | ------------------- |
| **`id`** | <code>string</code> |


### Type Aliases


#### Identity

The various identity configurations that you can use when initializing a
`Ditto` instance.

<code><a href="#identityofflineplayground">IdentityOfflinePlayground</a> | <a href="#identitysharedkey">IdentitySharedKey</a> | <a href="#identitymanual">IdentityManual</a> | <a href="#identityonlineplayground">IdentityOnlinePlayground</a> | <a href="#identityonlinewithauthentication">IdentityOnlineWithAuthentication</a></code>


#### InitOptions

Available options for {@link init | init()}.

<code>{ /** * You can explicitly pass the WebAssembly module or its location via the * `webAssemblyModule` option. By default, Ditto tries to load the WebAssembly * module from the same path where this JavaScript is served. */ webAssemblyModule?: <a href="#webassemblymodule">WebAssemblyModule</a>; }</code>


#### WebAssemblyModule

Various options to pass the web assembly module to Ditto.

<code>RequestInfo | URL | Response | BufferSource | WebAssembly.Module | string | null</code>


#### DocumentValue

A document value is a JavaScript object containing values for keys that
can be serialized via CBOR.

<code><a href="#record">Record</a>&lt;string, any&gt;</code>


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>


#### UpsertOptions

<code>{ writeStrategy?: <a href="#writestrategy">WriteStrategy</a>; }</code>


#### WriteStrategy

Defines the various strategies available when inserting a document into a
collection.

- `merge`: the existing document will be merged with the document being
   inserted, if there is a pre-existing document.

- `insertIfAbsent`: insert the document only if there is not already a
   document with the same ID in the store. If there is already a document in
   the store with the same ID then this will be a no-op.

- `insertDefaultIfAbsent`: insert the document, with its contents treated as
   default data, only if there is not already a document with the same ID in
   the store. If there is already a document in the store with the same ID
   then this will be a no-op. Use this strategy if you want to insert default
   data for a given document ID, which you want to treat as common initial
   data amongst all peers and that you expect to be mutated or overwritten in
   due course.

<code>'merge' | 'insertIfAbsent' | 'insertDefaultIfAbsent'</code>


#### QueryArguments

Represents a dictionary of values to be incorporated into a query keyed
by the placeholder used within that query. See method
{@link Collection.find | find()} of {@link Collection} for more info.

<code>{ [key: string]: any; }</code>


#### SortDirection

Describes the direction when sorting a query.

<code>'ascending' | 'descending'</code>


#### ObserveCallback

<code>(evt: <a href="#observeevent">ObserveEvent</a>&lt;T&gt;): void</code>


#### ObserveEvent

<code>{ docs: T[] }</code>


#### CallbackId

<code>string</code>

</docgen-api>
