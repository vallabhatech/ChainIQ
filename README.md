# ChainIQ / ResiliGraph

> AI Crisis Navigator for Supply Chains — an Android/Jetpack Compose prototype for exploring supply-chain disruption, impact, recovery scenarios, resilience, policy rules, and approval workflows.

## What it is

ChainIQ contains the ResiliGraph Android application. The current implementation is a UI-first interactive prototype backed mainly by in-memory mock data and deterministic simulations.

The demo models a supplier disruption and lets users explore:

- incident reporting
- incident analysis
- impact radius and time-machine scenarios
- recovery options
- supply-network dependencies
- resilience scoring
- policy rules
- recommendation and challenge flows
- approval and recovery completion

> Important: Firebase AI/Gemini, Retrofit, Room and other infrastructure dependencies are present, but the inspected Kotlin implementation currently uses local mock/simulation logic rather than a live backend or live Gemini request path.

## Architecture

~~~mermaid
flowchart TD
    User[User] --> Activity[MainActivity]
    Activity --> Navigation[Navigation Compose]
    Navigation --> Screens[Compose Screens]
    Screens --> VM[ResiliGraphViewModel]
    VM --> Mock[MockData]
    VM --> State[StateFlow]
    State --> Screens
    Screens --> Components[Reusable Components]
    Components --> Theme[Material 3 Theme]
~~~

## Main flow

~~~text
Report disruption
      ↓
Incident analysis
      ↓
Impact analysis
      ↓
Recovery simulation
      ↓
Recovery comparison
      ↓
Recommendation
      ↓
Challenge AI / hidden dependencies
      ↓
Supply graph
      ↓
Resilience score
      ↓
Policy brain
      ↓
Approval
      ↓
Recovery success
~~~

## Technology

| Area | Implementation |
|---|---|
| Platform | Android |
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigation | Navigation Compose |
| State | ViewModel + StateFlow |
| Build | Gradle / Android Gradle Plugin |
| Java | Source/target 11; CI JDK 17 |
| Minimum Android | API 24 |
| Target Android | API 36 |
| Compile Android | API 36.1 |
| Testing | JUnit, AndroidX Test, Espresso, Robolectric, Compose UI Test, Roborazzi |
| CI | GitHub Actions |

## Quick start

### Prerequisites

- JDK 17 recommended
- Android SDK API 36 / 36.1
- Gradle 9.3.1
- Android Studio with current Kotlin/Compose support
- Android emulator or device with API 24+

The repository has Gradle wrapper configuration but the inspected tree does not contain gradlew or gradlew.bat. The current CI workflow therefore invokes installed Gradle directly.

### Build

~~~bash
gradle assembleDebug
~~~

CI uses:

~~~bash
gradle assembleDebug --stacktrace --no-daemon
~~~

APK output:

~~~text
app/build/outputs/apk/debug/
~~~

## Configuration

The repository includes .env.example with a GEMINI_API_KEY placeholder. The Android Secrets Gradle Plugin is configured to read .env and .env.example.

Never commit real credentials.

## Testing

The repository includes local unit tests, Robolectric tests, Android/instrumented tests, Compose UI testing and Roborazzi screenshot-test infrastructure.

See docs/testing.md.

## CI/CD

.github/workflows/build-apk.yml builds a debug APK on pushes and pull requests targeting main, plus manual dispatch. The artifact is retained for 14 days.

See docs/ci-cd.md.

## Documentation

- docs/overview.md — product and implementation overview
- docs/getting-started.md — setup and build
- docs/architecture.md — system design
- docs/workflows.md — user and system workflows
- docs/project-structure.md — source map
- docs/configuration.md — configuration and secrets
- docs/testing.md — tests
- docs/ci-cd.md — GitHub Actions
- docs/troubleshooting.md — common issues
- docs/security.md — security notes
- docs/llm-context.md — coding-agent context

## Current implementation status

Treat the project as an interactive prototype/demo, not a complete production supply-chain platform.

Observed implementation facts:

- default data comes from MockData
- application state is held in StateFlow
- voice input is simulated
- document upload is simulated
- analysis is simulated with coroutine delays
- chat responses are deterministic keyword-based responses
- approval is local state
- no active persistence implementation was identified
- no active application-specific backend API was identified
- no live Gemini call was identified in the inspected Kotlin source

## License

No license file was present in the inspected repository tree.
