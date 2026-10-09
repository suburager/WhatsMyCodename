# What's My Codename?

**English** | [Русский](README.ru.md)

A very simple and easy tool to find out your legacy Android device's codename (e.g., i9100, i9300, jflte, maguro, mako, hammerhead) and related values.

<p align="center">
  <img src="screenshots/screenshot.png" alt="What's My Codename? on a Samsung Galaxy S3" width="320">
</p>

## Download

Get `whatsmycodename.apk` from the [latest release](https://github.com/suburager/whatsmycodename/releases/latest). Works on Android 1.5 and newer.

## Build

Requirements: JDK 21, Android SDK (platform 34, build-tools 34.0.0), Gradle 8.10.2.

Create `local.properties` in the project root:

```
sdk.dir=/path/to/android-sdk
```

Then run:

```
gradle assembleDebug
```

The APK will be at `app/build/outputs/apk/debug/app-debug.apk`.

## License

[MIT](LICENSE)
