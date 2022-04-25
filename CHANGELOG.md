# Changelog

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
