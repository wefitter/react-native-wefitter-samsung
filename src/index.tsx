import { NativeModules } from 'react-native';

export enum Error {
  TokenNotValid = 'Token not valid',
  ApiUrlNotValid = 'Api url not valid',
  NotConfigured = 'Not configured',
  ConfigureNotSucceeded = 'Configure not succeeded',
  PermissionHeartRateNotAccepted = 'Permission for heart rate not accepted',
  PermissionHeightNotAccepted = 'Permission for height not accepted',
  PermissionWeightNotAccepted = 'Permission for weight not accepted',
  PermissionSleepStageNotAccepted = 'Permission for sleep stage not accepted',
  PermissionExerciseNotAccepted = 'Permission for exercise not accepted',
  PermissionStepsDailyTrendNotAccepted = 'Permission for steps daily trend not accepted',
  SamsungHealthPleaseInstall = 'Please install Samsung Health',
  SamsungHealthPleaseUpdate = 'Please update Samsung Health',
  SamsungHealthPleaseEnable = 'Please enable Samsung Health',
  SamsungHealthPleaseAgree = 'Please agree to Samsung Health policy',
  SamsungHealthPleaseUnlock = 'Please unlock Samsung Health',
  SamsungHealthPleaseMakeAvailable = 'Please make Samsung Health available',
  ConnectionNotAvailable = 'Connection with Samsung Health is not available',
  AcceptAtLeastOnePermission = 'At least one permission should be accepted',
}

export type ConfiguredEvent = {
  configured: boolean;
};

export type ConnectedEvent = {
  connected: boolean;
};

export type ErrorEvent = {
  error: Error;
  forUser: boolean;
};

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
  tryToResolveError(error: Error): void;
};

const { WeFitterSamsung } = NativeModules;

export default WeFitterSamsung as WeFitterSamsungType;
