import React, { useEffect, useState } from 'react';
import {
  StyleSheet,
  View,
  Text,
  NativeEventEmitter,
  Button,
  Platform,
  Alert,
} from 'react-native';
import WeFitterSamsung, {
  ConfiguredEvent,
  ConnectedEvent,
  ErrorEvent,
} from 'react-native-wefitter-samsung';

export default function App() {
  const [connected, setConnected] = useState<boolean>(false);

  const prefix = 'com.samsung.health';
  // Setup your Samsung approved permissions here
  // The user will see these permissions only in the permission selection screen
  const myAppPermissions: string[] = [
    `com.samsung.shealth.step_daily_trend`,
    `${prefix}.heart_rate`,
    `${prefix}.height`,
    `${prefix}.weight`,
    `${prefix}.sleep_stage`,
    `${prefix}.exercise`,
    //"$prefix.blood_glucose",
    //"$prefix.blood_pressure",
    //"$prefix.body_fat",
    //"$prefix.body_temperature",
    //"$prefix.oxygen_saturation",'
  ];
  const myAppPermissionsString = myAppPermissions.join(',');

  useEffect(() => {
    if (Platform.OS === 'android') {
      // create native event emitter and event listeners to handle status updates
      const emitter = new NativeEventEmitter();
      const configuredListener = emitter.addListener(
        'onConfiguredWeFitterSamsung',
        (event: ConfiguredEvent) =>
          console.log(`WeFitterSamsung configured: ${event.configured}`)
      );
      const connectedListener = emitter.addListener(
        'onConnectedWeFitterSamsung',
        (event: ConnectedEvent) => {
          console.log(`WeFitterSamsung connected: ${event.connected}`);
          setConnected(event.connected);
        }
      );
      const errorListener = emitter.addListener(
        'onErrorWeFitterSamsung',
        (event: ErrorEvent) => {
          console.log(`WeFitterSamsung error: ${event.error}`);

          // `error` can be checked to override specific messages
          // `forUser` boolean indicates the error requires user interaction
          if (event.forUser) {
            Alert.alert('', event.error, [
              {
                text: 'OK',
                onPress: () => {
                  // `tryToResolveError` will guide the user on fixing certain errors
                  // for example:
                  // - link to the app store to install or update Samsung Health
                  // - open Samsung Health to accept privacy policy
                  // - open Settings when Samsung Health needs to be enabled
                  WeFitterSamsung.tryToResolveError(event.error);
                },
              },
            ]);
          }
        }
      );

      // create config
      const config = {
        token: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJUZXN0MSIsImFwcCI6IjI0MDBkYjdlLWUyOGUtNGU2MC1iZDIzLTgwN2JkMjNmYjQ1OSIsImlhdCI6MTcyNjYzNzQxNSwiaWQiOiIwODQwNzkxYS0yMzMxLTQ1NzQtYmQ4OS0yOTA3Y2YzNTkwNzIifQ.JBqJ7BQKOmcamnITCvqclwvT5IZZ1bRX6utV85ZimBA', // required, WeFitter API profile bearer token
        apiUrl: 'https://test1.wefitter.com/api/', // optional, only use if you want to use your backend as a proxy and forward all API calls to the WeFitter API. Default: `https://api.wefitter.com/api/`
        //startDate: 'CUSTOM_START_DATE', // optional with format `yyyy-MM-dd`, by default data of the past 7 days will be uploaded
        notificationTitle: 'CUSTOM_TITLE', // optional
        notificationText: 'CUSTOM_TEXT', // optional
        notificationIcon: 'ic_notification', // optional, e.g. `ic_notification` placed in either drawable, mipmap or raw
        notificationChannelId: 'CUSTOM_CHANNEL_ID', // optional
        notificationChannelName: 'CUSTOM_CHANNEL_NAME', // optional
        myAppPermissions: myAppPermissionsString,
        configFGSPermissions: 'false',
      };

      // configure WeFitterSamsung
      WeFitterSamsung.configure(config);

      return () => {
        configuredListener.remove();
        connectedListener.remove();
        errorListener.remove();
      };
    }
    return;
  }, []);

  const onPressConnectOrDisconnect = () => {
    if (Platform.OS === 'android') {
      connected ? WeFitterSamsung.disconnect() : WeFitterSamsung.connect();
    } else {
      Alert.alert('Not supported', 'WeFitterSamsung is not supported on iOS');
    }
  };

  return (
    <View style={styles.container}>
      <Text>Connected: {'' + connected}</Text>
      <Button
        onPress={onPressConnectOrDisconnect}
        title={connected ? 'Disconnect' : 'Connect'}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
