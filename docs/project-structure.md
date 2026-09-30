# Project Structure

~~~text
ChainIQ/
├── .env.example
├── .github/workflows/build-apk.yml
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── androidTest/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/example/
│       │   │   ├── MainActivity.kt
│       │   │   ├── data/
│       │   │   ├── ui/
│       │   │   │   ├── components/
│       │   │   │   ├── navigation/
│       │   │   │   ├── screens/
│       │   │   │   └── theme/
│       │   │   └── viewmodel/
│       │   └── res/
│       └── test/
├── build.gradle.kts
├── gradle/libs.versions.toml
├── gradle/wrapper/
├── gradle.properties
├── metadata.json
└── settings.gradle.kts
~~~

## Important files

MainActivity.kt is the application shell and navigation entry point.

data/Models.kt defines the domain objects.

data/MockData.kt contains the demo incident, recovery options, timeline data, network graph, business rules and chat examples.

viewmodel/ResiliGraphViewModel.kt owns interactive state and event handlers.

ui/navigation/Routes.kt centralizes route constants.

ui/screens contains feature screens.

ui/components contains reusable Compose components.

ui/theme contains color, typography and Material 3 theme configuration.

## Build configuration

Current app configuration:

| Setting | Value |
|---|---|
| Namespace | com.example |
| Application ID | com.aistudio.resiligraph.vckzpq |
| minSdk | 24 |
| targetSdk | 36 |
| compileSdk | 36.1 |
| versionCode | 1 |
| versionName | 1.0 |
| Java source/target | 11 |

## Naming note

The GitHub repository is ChainIQ while the Gradle root project and metadata identify the application as ResiliGraph. Preserve that distinction in future documentation.
