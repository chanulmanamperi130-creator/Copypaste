# Unicode Clipboard Manager for Android

## Overview
This Android app receives shared text from ChatGPT (or any app) and preserves full Unicode characters, then copies them to your clipboard for pasting into Google Docs or other applications.

## Features
- Receives shared text via Android's share menu
- Preserves full UTF-8/Unicode encoding
- Auto-copies to clipboard when text is received
- Manual copy and clear buttons
- Displays received text for verification

## How to Build

### Prerequisites
- Android Studio (latest version recommended)
- Java Development Kit (JDK) 8 or higher
- Android SDK

### Build Steps

1. **Open Android Studio**
   - Click "Open an Existing Project"
   - Navigate to the `UnicodeClipboard` folder
   - Click OK

2. **Sync Gradle**
   - Android Studio should automatically sync Gradle files
   - If not, click "File > Sync Project with Gradle Files"

3. **Build APK**
   - Click "Build > Build Bundle(s) / APK(s) > Build APK(s)"
   - Wait for build to complete
   - APK will be in `app/build/outputs/apk/debug/app-debug.apk`

4. **Install on Device**
   - Connect your Android device via USB (enable USB debugging)
   - OR use Android Emulator
   - Click "Run > Run 'app'" or click the green play button

## How to Use

1. **In ChatGPT (or any app):**
   - Select text you want to copy
   - Tap "Share" button
   - Choose "Unicode Clipboard" from the share menu

2. **In Unicode Clipboard app:**
   - Text appears automatically
   - Text is auto-copied to clipboard
   - You can manually tap "Copy to Clipboard" if needed

3. **In Google Docs (or any app):**
   - Long-press where you want to paste
   - Tap "Paste"
   - Full Unicode text will be pasted

## File Structure
```
UnicodeClipboard/
├── app/
│   ├── build.gradle
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/unicodeclipboard/app/
│           │   └── MainActivity.java
│           └── res/
│               ├── layout/
│               │   └── activity_main.xml
│               └── values/
│                   └── strings.xml
└── build.gradle (project level - create if needed)
```

## Troubleshooting

**App doesn't appear in share menu:**
- Make sure app is installed
- Try restarting your device
- Check AndroidManifest.xml has correct intent-filter

**Unicode not displaying correctly:**
- Ensure target app (like Google Docs) supports Unicode
- Some apps may have font limitations

**Build errors:**
- Update Android Studio to latest version
- Sync Gradle files
- Check SDK versions match your installed SDK

## Technical Details
- Minimum SDK: API 21 (Android 5.0)
- Target SDK: API 33 (Android 13)
- Encoding: UTF-8 (Unicode)
- Language: Java

## License
Free to use and modify.
