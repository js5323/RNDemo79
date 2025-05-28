import { TurboModule, TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  // 扫描模块方法
  startScan(type: string): Promise<{ type: string, data: string, timestamp: number }>;
  stopScan(): Promise<void>;
  isScanning(): boolean;
}

export default TurboModuleRegistry.get<Spec>('RTNMpaas') as Spec | null;
