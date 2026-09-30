# CI/CD

Workflow: .github/workflows/build-apk.yml

## Triggers

- push to main
- pull request targeting main
- manual workflow dispatch

## Environment

- ubuntu-latest
- Temurin JDK 17
- Gradle 9.3.1

## Pipeline

~~~mermaid
flowchart TD
    Trigger[Push / PR / Manual] --> Checkout[Checkout]
    Checkout --> Java[JDK 17]
    Java --> Gradle[Gradle 9.3.1]
    Gradle --> Keystore[Create CI Debug Keystore]
    Keystore --> Verify[Verify configuration]
    Verify --> Build[assembleDebug]
    Build --> Artifact[Upload APK]
~~~

## Build

~~~bash
gradle assembleDebug --stacktrace --no-daemon
~~~

The workflow creates a CI debug keystore, builds the debug APK and uploads app/build/outputs/apk/debug/*.apk.

Artifact name: ChainIQ-debug-apk.

Retention: 14 days.

## Deployment boundary

This workflow does not publish to Google Play or another production distribution channel. It creates a downloadable debug artifact.
