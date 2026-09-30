# Getting Started

## Prerequisites

Use:

- JDK 17
- Android SDK API 36 / 36.1
- Gradle 9.3.1
- Android Studio with Kotlin/Compose support
- Android device or emulator with API 24+

The GitHub Actions workflow is the strongest reproducible reference: it uses Temurin JDK 17 and Gradle 9.3.1.

## Clone

~~~bash
git clone https://github.com/vallabhatech/ChainIQ.git
cd ChainIQ
~~~

## Configure

The repository contains .env.example. If you need local secret configuration, create .env from that template.

The current template documents a GEMINI_API_KEY placeholder.

Never commit .env or real keys.

## Build

~~~bash
gradle assembleDebug
~~~

CI uses:

~~~bash
gradle assembleDebug --stacktrace --no-daemon
~~~

The debug APK is generated under app/build/outputs/apk/debug/.

## Android Studio

Open the repository root as a Gradle project, sync dependencies, install the required Android SDK and run the app on an API 24+ device/emulator.

## Source walkthrough

Start with this order:

1. app/src/main/java/com/example/MainActivity.kt
2. app/src/main/java/com/example/ui/navigation/Routes.kt
3. app/src/main/java/com/example/viewmodel/ResiliGraphViewModel.kt
4. app/src/main/java/com/example/data/Models.kt
5. app/src/main/java/com/example/data/MockData.kt
6. app/src/main/java/com/example/ui/screens/
7. app/src/main/java/com/example/ui/components/
8. app/src/main/java/com/example/ui/theme/

This gives the navigation → state → data → UI path.

## Gradle wrapper note

The repository contains gradle/wrapper/gradle-wrapper.properties, but the inspected tree does not contain gradlew or gradlew.bat. Use Android Studio's Gradle integration or an installed compatible Gradle version.

## Useful commands

~~~bash
gradle assembleDebug
gradle test
gradle connectedAndroidTest
~~~

Instrumented tests require a connected Android device/emulator.
