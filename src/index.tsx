import { NativeModules } from 'react-native';

type WeFitterSamsungType = {
  configure(config: {
    token: string;
    apiUrl: string;
    notificationTitle?: string;
    notificationText?: string;
    notificationIcon?: string;
    notificationChannelId?: string;
    notificationChannelName?: string;
  }): void;
  connect(): void;
  disconnect(): void;
  isConnected(callback: (connected: boolean) => void): void;
};

const { WeFitterSamsung } = NativeModules;

export default WeFitterSamsung as WeFitterSamsungType;
