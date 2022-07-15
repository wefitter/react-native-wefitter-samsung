# react-native-wefitter-samsung

React Native library for integrating WeFitter and Samsung Health into your app.

## Requirements

- Kotlin
  - See this [guide](https://developer.android.com/kotlin/add-kotlin) on how to add Kotlin support to your app.
- minSdkVersion 23
  - See this [link](https://developer.android.com/studio/publish/versioning#minsdkversion) for more information.

## Getting started

For all new integrations, access has to be requested from Samsung Health. The approval process is subject to change at any time. To get started please contact [WeFitter API Support](mailto:api-support@wefitter.com).

You do have to upload an app to the Play Console before your app can be approved. It does not have to be released to the public. Keep the following information on hand during the approval process:

- App application ID
- SHA-256 signing key certificate (found on Play Console in App integrity section)
- List of supported countries
- List of datatypes (see below)

### Datatypes

During the approval process you will be asked for a list of datatypes. This SDK supports the following datatypes so during the approval process please ask read access for at least the following:

- Blood glucose
- Blood pressure
- Body fat
- Body temperature
- Oxygen saturation
- Heart rate
- Height
- Weight
- Daily step count trend
- Sleep stage
- Exercise

It is possible to request less, but at least one from this list should be requested. The user will not be able to give permission to a datatype if your app isn't approved to access that datatype.

### Developer mode

To add developer mode access after your app has been approved see [Developer mode](https://developer.samsung.com/health/android/data/guide/dev-mode.html).

To use Samsung Health without developer mode make sure you build the app in release mode and the application ID and SHA-256 signing key certificate are registered at Samsung Health during the approval process.

### Historical data

When the connection has been enabled for the first time it will fetch data of the past 7 days. To override this you can pass a `startDate` in the `configure` function.

## Installation

```sh
yarn add https://github.com/ThunderbyteAI/react-native-wefitter-samsung.git#v1.3.0
```

## Usage

Add the following code and change `YOUR_TOKEN` and `YOUR_API_URL`:

```ts
import WeFitterSamsung, {
  ConfiguredEvent,
  ConnectedEvent,
  ErrorEvent,
} from 'react-native-wefitter-samsung';

// ...

const [connected, setConnected] = useState<boolean>(false);

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
      token: 'YOUR_TOKEN', // required
      apiUrl: 'YOUR_API_URL', // required, the url should be base without `v1/ingest/` as the library will append this. For example: `https://api.wefitter.com/api/`
      startDate: 'CUSTOM_START_DATE', // optional with format `yyyy-MM-dd`, by default data of the past 7 days will be uploaded
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
