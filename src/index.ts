import { registerPlugin } from '@capacitor/core';

import type { CapacitorDittoPlugin } from './definitions';

const CapacitorDitto = registerPlugin<CapacitorDittoPlugin>('CapacitorDitto', {
  web: () => import('./web').then((m) => new m.CapacitorDittoPluginWeb()),
});

export * from './definitions';
export { CapacitorDitto };
