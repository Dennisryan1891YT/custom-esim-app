# Project Overview

This repository contains a starter Android app prototype for a custom E-SIM application with onboarding, permissions, connection type selection, provider selection, and settings pages.

## Included screens
- Welcome screen with "Hi there!"
- Language selection with English variants and additional language options
- Setup permissions screen
- Connection type selection (VoLTE, 1G, 2G, 3G, LTE, LTE+, 4G, 5G)
- Service provider selection
- Home screen: Messaging, Phone Dialer, Settings
- Settings section with theme options

## Notes
This project is a UI prototype and architecture scaffold. Some device features in the original app request require manufacturer-specific APIs or privileged platform integration and are not typically available in a standard app without deeper Android system integration.

## Getting started
1. Open the project in Android Studio.
2. Let Gradle sync.
3. Run the app on an Android emulator or device.

## Main package
- `app/src/main/java/com/example/customesim/MainActivity.kt`
