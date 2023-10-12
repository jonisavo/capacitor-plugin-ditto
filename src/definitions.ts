import type { DocumentValue, Identity, QueryArguments, SortDirection, InitOptions as WebInitOptions } from "@dittolive/ditto";

export interface InitOptions {
  web?: WebInitOptions;
  identity?: Identity;
}

export interface UpsertOptions {
  collectionName: string;
  data: DocumentValue;
}

export interface SubscribeOptions {
  collectionName: string;
  queryString: string;
  queryArgs?: QueryArguments;
  limit?: number;
}

export interface CancelSubscriptionOptions {
  subscriptionId: string;
}

export interface StartLiveQueryOptions {
  collectionName: string;
  queryString: string;
  queryArgs?: QueryArguments;
  limit?: number;
  sort?: { propertyPath: string; sortDirection?: SortDirection }
}

export interface StopLiveQueryOptions {
  id: string;
}

export type CallbackId = string;
type ObserveEvent<T> = { docs: T[] }
export type ObserveCallback<T = any> = (evt: ObserveEvent<T>) => void;

export interface CapacitorDittoPlugin {
  init(options?: InitOptions): Promise<void>;
  isInitialized(): Promise<{ isInitialized: boolean }>
  checkDittoPermissions(): Promise<void>;
  startSync(): Promise<void>;
  stopSync(): Promise<void>;
  upsert(options: UpsertOptions): Promise<{ documentId: string }>;
  subscribe(options: SubscribeOptions): Promise<{ subscriptionId: string }>;
  cancelSubscription(options: CancelSubscriptionOptions): Promise<void>;
  startLiveQuery<T>(options: StartLiveQueryOptions, callback: ObserveCallback<T>): Promise<CallbackId>;
  stopLiveQuery(options: StopLiveQueryOptions): Promise<void>;
}
