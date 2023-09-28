export interface CapacitorDittoPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
