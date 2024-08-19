# Changelog

## 2.1.1

Improved Android 14 support

Foreground Service added for health data collection

Upgraded several packages

## 2.1.0

Android 14 support

SDK 34 added

Upgraded several packages

## 2.0.2

Moved some processing to different threads

## 2.0.1

Fixed an issue where daily summaries with steps were not uploaded when heart rate permission was missing

## 2.0.0

Updated dependencies

Added "Version" header to ingest call

**BREAKING CHANGE**

- Changed `implementation` to `compileOnly` for direct local .aar dependencies due to changes in the Android Gradle Plugin.

From now on the `.aar` dependencies should be manually added to the `android/apps/libs` folder in your app project and the following to your `app/build.gradle`:

```
dependencies {
  implementation fileTree(dir: 'libs', include: ['*.aar'])
}
```

You can find the necessary files [here](https://github.com/wefitter/react-native-wefitter-samsung/tree/v2.0.0/android/libs). Make sure you select the same version tag you are using in your app before downloading to prevent incompatibility issues.

## 1.5.1

Changed default start date from 7 to 20 days back

Fixed sleep data check

## 1.5.0

Updated dependencies

## 1.4.0

Added more detailed heart rate data to workouts

## 1.3.0

Added daily detail samples per 10 minutes containing steps, distance and energy

Added end date to heart rate samples

Fixed crash that occurred when app was restored from backup or migrated from another device

## 1.2.0

Added heart rate samples per 15 minutes

Fixed start date default

## 1.1.0

Added `startDate` to `configure`

Added the following datatypes:

- Blood glucose
- Blood pressure
- Body fat
- Body temperature
- Oxygen saturation

## 1.0.0

Error handling has been refactored

- This library will not show alerts to the user anymore and will pass
  on typed errors
- Added event and error typings
- Added `tryToResolveError` function to help the user solve specific errors

**BREAKING CHANGE**

- Changed error event type to `ErrorEvent`

## 0.0.2

Changed permissions request so that at least one must be accepted instead of all

## 0.0.1

First version
