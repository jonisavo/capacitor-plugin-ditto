import { WebPlugin } from '@capacitor/core';
import type { LiveQuery, Subscription } from '@dittolive/ditto';
import { Ditto, init } from '@dittolive/ditto';

import type {
  CallbackId,
  CancelSubscriptionOptions,
  CapacitorDittoPlugin,
  InitOptions,
  ObserveCallback,
  StartLiveQueryOptions,
  StopLiveQueryOptions,
  SubscribeOptions,
  UpsertOptions,
} from './definitions';

export class NoDittoError extends Error {
  constructor() {
    super('Ditto not initialized');
    this.name = 'NoDittoError';
  }
}

export class CapacitorDittoPluginWeb extends WebPlugin implements CapacitorDittoPlugin {
  private ditto?: Ditto;
  private readonly subscriptions: Map<string, Subscription> = new Map();
  private readonly liveQueries: Map<string, LiveQuery> = new Map();
  private liveQueryId = 0;

  async init(options?: InitOptions): Promise<void> {
    await init(options?.web);

    this.ditto = new Ditto(options?.identity);
  }

  isInitialized(): Promise<{ isInitialized: boolean }> {
    return Promise.resolve({ isInitialized: this.ditto !== undefined });
  }

  checkDittoPermissions(): Promise<void> {
    return Promise.resolve();
  }

  async upsert(options: UpsertOptions): Promise<{ documentId: string }> {
    if (this.ditto === undefined) {
      throw new NoDittoError();
    }

    const { collectionName, data } = options;

    const collection = this.ditto.store.collection(collectionName);
    const documentId = await collection.upsert(data);

    return { documentId: documentId.toString() };
  }

  subscribe(options: SubscribeOptions): Promise<{ subscriptionId: string }> {
    if (this.ditto === undefined) {
      return Promise.reject(new NoDittoError());
    }

    const { collectionName, queryString, queryArgs, limit } = options;

    const collection = this.ditto.store.collection(collectionName);

    let operation = collection.find(queryString, queryArgs);

    if (limit !== undefined) {
      operation = operation.limit(limit);
    }

    const subscription = operation.subscribe();
    const subscriptionId = subscription.contextInfo.id;

    this.subscriptions.set(subscriptionId, subscription);

    return Promise.resolve({ subscriptionId });
  }

  cancelSubscription(options: CancelSubscriptionOptions): Promise<void> {
    if (this.ditto === undefined) {
      return Promise.reject(new NoDittoError());
    }

    const { subscriptionId } = options;

    const subscription = this.subscriptions.get(subscriptionId);

    if (subscription === undefined) {
      return Promise.reject(new Error(`Subscription ${subscriptionId} not found`));
    }

    subscription.cancel();
    this.subscriptions.delete(subscriptionId);

    return Promise.resolve();
  }

  startLiveQuery<T>(options: StartLiveQueryOptions, callback: ObserveCallback<T>): Promise<CallbackId> {
    if (this.ditto === undefined) {
      return Promise.reject(new NoDittoError());
    }

    const { collectionName, queryString, queryArgs, limit, sort } = options;

    const collection = this.ditto.store.collection(collectionName);

    let operation = collection.find(queryString, queryArgs);

    if (limit !== undefined) {
      operation = operation.limit(limit);
    }

    if (sort !== undefined) {
      operation = operation.sort(sort.propertyPath, sort.sortDirection);
    }

    const liveQuery = operation.observeLocal((messages, _evt) => {
      const mappedMessages = messages.map((msg) => msg.value as T);
      callback({ docs: mappedMessages });
    });

    this.liveQueries.set(this.liveQueryId.toString(), liveQuery);

    const liveQueryIdString = this.liveQueryId.toString();

    this.liveQueryId++;

    return Promise.resolve(liveQueryIdString);
  }

  stopLiveQuery(options: StopLiveQueryOptions): Promise<void> {
    if (this.ditto === undefined) {
      return Promise.reject(new NoDittoError());
    }

    const { id } = options;

    const liveQuery = this.liveQueries.get(id);

    if (liveQuery === undefined) {
      return Promise.reject(new Error(`Live query ${id} not found`));
    }

    liveQuery.stop();
    this.liveQueries.delete(id);

    return Promise.resolve();
  }

  startSync(): Promise<void> {
    if (this.ditto === undefined) {
      return Promise.reject(new NoDittoError());
    }

    this.ditto.startSync();

    return Promise.resolve();
  }

  stopSync(): Promise<void> {
    if (this.ditto === undefined) {
      return Promise.reject(new NoDittoError());
    }

    this.ditto.stopSync();

    return Promise.resolve();
  }
}
