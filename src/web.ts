import { WebPlugin } from '@capacitor/core';

import type { CapacitorDittoPluginPlugin } from './definitions';

export class CapacitorDittoPluginWeb
  extends WebPlugin
  implements CapacitorDittoPluginPlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
