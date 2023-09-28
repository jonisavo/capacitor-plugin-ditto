import { registerPlugin } from '@capacitor/core';

import type { CapacitorDittoPluginPlugin } from './definitions';

const CapacitorDittoPlugin = registerPlugin<CapacitorDittoPluginPlugin>(
  'CapacitorDittoPlugin',
  {
    web: () => import('./web').then(m => new m.CapacitorDittoPluginWeb()),
  },
);

export * from './definitions';
export { CapacitorDittoPlugin };
