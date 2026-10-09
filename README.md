# What's My Codename?

A very simple and easy tool to find out your legacy Android device's codename (e.g., i9100, i9300, jflte, maguro, mako, hammerhead) and related values.

<p align="center">
  <img src="screenshots/screenshot.png" alt="What's My Codename? running on a Samsung Galaxy S3 (GT-I9300), showing codename m0" width="320">
</p>

## Download

Готовый APK лежит в разделе [**Releases**](../../releases/latest) — скачай `whatsmycodename.apk`, скопируй на телефон и установи (может потребоваться разрешить установку из неизвестных источников).

## What it shows

| Field | Meaning |
|---|---|
| **Device (codename)** | `Build.DEVICE` — само кодовое имя (`m0`, `mako`, `rain`…) |
| Product | `Build.PRODUCT` |
| Board | `Build.BOARD` |
| Hardware | `Build.HARDWARE` |
| Model | `Build.MODEL` |
| Manufacturer | `Build.MANUFACTURER` |

Никакой сети и внешних баз — все данные берутся из `android.os.Build`.

## Compatibility

Интерфейс в стиле Android Holo (4.x, 2012-2013); для старых версий (1.5-2.3.7, где Holo ещё не существовал) — отдельная fallback-тема. Работает от Android 1.5 (`minSdkVersion 3`) до самых новых версий.

Проверено на: Samsung Galaxy S3 (`GT-I9300`, codename `m0`), Xiaomi Redmi 10C (`rain`), HTC Desire 616 (`htc_v3_dug`).

## License

MIT — см. файл [LICENSE](LICENSE).

## Сборка

Перед первой сборкой создайте `local.properties` в корне проекта
(этот файл в `.gitignore`, у каждого свой путь к SDK):
```
sdk.dir=/путь/к/вашему/android-sdk
```

### Через Termux (на Android-устройстве)

```bash
pkg install wget openjdk-21 aapt aapt2 git unzip -y

# Android SDK command-line tools
wget https://dl.google.com/android/repository/commandlinetools-linux-15859902_latest.zip
mkdir -p ~/android-sdk/cmdline-tools
unzip commandlinetools-linux-15859902_latest.zip -d ~/android-sdk/cmdline-tools
mv ~/android-sdk/cmdline-tools/cmdline-tools ~/android-sdk/cmdline-tools/latest
export ANDROID_HOME=$HOME/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools
yes | sdkmanager --licenses
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# Gradle
wget -O gradle.zip https://services.gradle.org/distributions/gradle-8.10.2-bin.zip
unzip gradle.zip -d ~/gradle
export PATH=$PATH:~/gradle/gradle-8.10.2/bin

# Фикс aapt2 под ARM + лимит памяти
mkdir -p ~/.gradle
cat > ~/.gradle/gradle.properties << 'GRADLEEOF'
android.aapt2FromMavenOverride=/data/data/com.termux/files/usr/bin/aapt2
org.gradle.jvmargs=-Xmx1536m
org.gradle.daemon=false
GRADLEEOF

cd WhatsMyCodename
gradle assembleDebug --no-daemon
```

### Через Windows / Linux / macOS (без Android Studio)

1. Установите JDK 21: https://adoptium.net
2. Скачайте Android SDK command-line tools:
   https://developer.android.com/studio (раздел "Command line tools only")
3. Распакуйте так, чтобы получилось `<sdk>/cmdline-tools/latest/bin/...`
4. Пропишите `ANDROID_HOME` и добавьте в `PATH`:
   `<sdk>/cmdline-tools/latest/bin`, `<sdk>/platform-tools`
5. `sdkmanager --licenses`
6. `sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"`
7. Скачайте Gradle 8.10.2: https://gradle.org/releases/ → добавьте
   `bin`-папку в PATH
8. В корне проекта создайте `local.properties` со своим `sdk.dir`
9. `gradle assembleDebug`

Готовый APK: `app/build/outputs/apk/debug/app-debug.apk`

## Как это работает

`Build.DEVICE` в подавляющем большинстве случаев и есть искомое
кодовое имя устройства. На некоторых моделях (например, Redmi 10C)
может отличаться от "общепринятого" кодового имени в сообществе
кастомных прошивок, если устройство выпускалось в нескольких
аппаратных вариантах (с NFC / без NFC и т.п.) — тогда у каждого
варианта своё реальное `Build.DEVICE`.
