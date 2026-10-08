# Custom E-Sim App - Version 17.0

## Overview
A custom Android E-SIM application with multi-language support, customizable themes, and font selection.

## Features
✅ Multi-language support (12+ languages)
✅ Customizable themes (Material You, Google Messages, Light/Dark variants)
✅ Custom font selection (Poppins, Droid Sans, Roboto variants, Roboto Flex)
✅ E-SIM provider selection
✅ Connection type selection (VoLTE, 1G-5G)
✅ Permissions management
✅ Settings dashboard
✅ Messaging, Phone Dialer tabs

## Build Info
- **Version:** 17.0
- **Version Code:** 17
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Language:** Kotlin + Jetpack Compose

## Building the APK

### Release APK:
```bash
./gradlew assembleRelease
```
Output: `app/build/outputs/apk/release/app-release.apk`

### Debug APK:
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

## Installation
```bash
adb install -r app/build/outputs/apk/release/app-release.apk
```

## Requirements
- Android Studio Hedgehog or later
- Android SDK 34
- JDK 17 or higher
- Gradle 8.5+

## Permissions Requested
- READ_PHONE_STATE
- READ_CALL_LOG
- READ_SMS, SEND_SMS
- CALL_PHONE
- CAMERA, RECORD_AUDIO
- ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION
- WRITE_SETTINGS
- REQUEST_INSTALL_PACKAGES
- WRITE_SECURE_SETTINGS

## Languages Supported
English (US, CA, IN, UK), French, Spanish, German, Japanese, Hindi, Portuguese, Chinese, Arabic

## License
Private Project

## Author
Dennisryan1891YT
