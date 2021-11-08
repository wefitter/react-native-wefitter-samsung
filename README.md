# react-native-wefitter-samsung

React Native library for WeFitter and Samsung Health

## Getting started

For all new integrations, access has to be requested from Samsung Health. For information please contact [WeFitter API Support](mailto:api-support@wefitter.com).

### Datatypes

During the approval process you will be asked for a list of datatypes. This SDK supports the following datatypes so during the approval process please ask read access for the following:

- Heart rate
- Height
- Weight
- Daily step count trend
- Sleep stage
- Exercise

It is possible to request less, but at least one from this list should be requested. The user will not be able to give permission to a datatype if your app isn't approved to access that datatype.

### Developer mode

To add developer mode access after your app has been approved see [Developer mode](https://developer.samsung.com/health/android/data/guide/dev-mode.html).

### Historical data

When the connection has been enabled for the first time it will fetch data of the last 24 hours. Sleep data is an exception to this as it will fetch data since 12pm UTC of the previous day. Further historical data is not available.

## Installation

```sh
yarn add git://github.com/ThunderbyteAI/react-native-wefitter-samsung.git#v0.0.2
```

## Usage

Add the following code and change `YOUR_TOKEN` and `YOUR_API_URL`:

```ts
import WeFitterSamsung from 'react-native-wefitter-samsung';

// ...

const [connected, setConnected] = useState<boolean>(false);

useEffect(() => {
  if (Platform.OS === 'android') {
    // create native event emitter and event listeners to handle status updates
    const emitter = new NativeEventEmitter();
    const configuredListener = emitter.addListener(
      'onConfiguredWeFitterSamsung',
      (event: { configured: boolean }) =>
        console.log(`WeFitterSamsung configured: ${event.configured}`)
    );
    const connectedListener = emitter.addListener(
      'onConnectedWeFitterSamsung',
      (event: { connected: boolean }) => {
        console.log(`WeFitterSamsung connected: ${event.connected}`);
        setConnected(event.connected);
      }
    );
    const errorListener = emitter.addListener(
      'onErrorWeFitterSamsung',
      (event: { error: string }) => {
        console.log(`WeFitterSamsung error: ${event.error}`);
      }
    );

    // create config
    const config = {
      token: 'YOUR_TOKEN', // required
      apiUrl: 'YOUR_API_URL', // required
      notificationTitle: 'CUSTOM_TITLE', // optional
      notificationText: 'CUSTOM_TEXT', // optional
      notificationIcon: 'CUSTOM_ICON', // optional, e.g. `ic_notification` placed in either drawable, mipmap or raw
      notificationChannelId: 'CUSTOM_CHANNEL_ID', // optional
      notificationChannelName: 'CUSTOM_CHANNEL_NAME', // optional
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
```

See the [example](example/src/App.tsx) for the full source.

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.
