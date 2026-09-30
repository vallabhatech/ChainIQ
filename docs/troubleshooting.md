# Troubleshooting

## Gradle not found

The CI workflow expects installed Gradle 9.3.1. The repository contains wrapper properties but the inspected tree does not contain gradlew or gradlew.bat.

Use Android Studio Gradle integration or install a compatible Gradle version.

## Build environment mismatch

Verify:

- JDK 17
- Android SDK API 36/36.1
- Gradle 9.3.1

Use:

~~~bash
gradle assembleDebug --stacktrace
~~~

## Google Services

google-services.json is not present in the inspected tree. The project configures the Google Services plugin to warn/passthrough when missing.

If a future runtime integration requires Firebase configuration, provide the correct project configuration.

## Gemini

GEMINI_API_KEY is documented, but the inspected Kotlin source does not show a live Gemini request path. Do not assume adding the key alone activates AI.

## Instrumented tests

Connect an Android emulator/device and run:

~~~bash
gradle connectedAndroidTest
~~~

## State resets

The prototype stores active state in memory and initializes from MockData. Values resetting after relaunch are therefore expected until persistence is implemented.

## Simulated features

Voice capture, document upload, analysis progression and chat responses are intentionally simulated in the current ViewModel.
