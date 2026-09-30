# LLM Context

Use this document as the repository-specific context for coding agents.

## Identity

- GitHub repository: vallabhatech/ChainIQ
- Gradle project: ResiliGraph
- Package: com.example
- Application ID: com.aistudio.resiligraph.vckzpq

## Core files

- MainActivity.kt — application shell and navigation
- ui/navigation/Routes.kt — routes
- viewmodel/ResiliGraphViewModel.kt — central state and event handling
- data/Models.kt — domain models
- data/MockData.kt — deterministic demo data
- ui/screens/ — feature screens
- ui/components/ — shared Compose components
- ui/theme/ — visual system

## Architecture rule

Treat the current implementation as a local simulation unless runtime code proves otherwise.

Do not infer active integrations from dependencies alone.

Firebase AI, Retrofit, OkHttp, Moshi, Room and authentication-related dependencies exist, but they are not automatically active services.

## New feature checklist

When adding a screen:

1. add a route to Routes.kt
2. register it in MainActivity.kt
3. place UI in ui/screens
4. reuse ui/components
5. add/update domain models when necessary
6. use ViewModel/StateFlow for shared prototype state
7. add tests
8. update docs when behavior or architecture changes

## Mock behavior

Current simulated behavior includes:

- voice capture
- document upload
- analysis progression
- AI/chat responses
- resilience scoring
- approval

Do not document these as real integrations unless implemented.

## Build

CI uses JDK 17 and Gradle 9.3.1.

Primary debug command:

~~~bash
gradle assembleDebug
~~~

## Documentation rule

The codebase is the source of truth.

When changing behavior, update the relevant documentation. Keep Mermaid diagrams synchronized with implementation. Never expose secrets.
