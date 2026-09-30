# Configuration

## Environment

.env.example documents a GEMINI_API_KEY placeholder.

The Secrets Gradle Plugin is configured with:

- propertiesFileName = .env
- defaultPropertiesFileName = .env.example

Do not commit .env or real credentials.

## Firebase / Gemini

Firebase AI is an active Gradle dependency and the repository metadata advertises a server-side Gemini capability, but the inspected Kotlin source does not show a live Gemini request.

Therefore the key should be treated as a configuration/integration point, not proof of active runtime AI generation.

## Google Services

The Google Services plugin is applied.

The build uses a warning/passthrough strategy when google-services.json is missing. No google-services.json file was present in the inspected tree.

## Release signing

Release signing reads:

- KEYSTORE_PATH
- STORE_PASSWORD
- KEY_PASSWORD

The configured key alias is upload.

Never store release passwords or production keystores in Git.

## Android values

- namespace: com.example
- applicationId: com.aistudio.resiligraph.vckzpq
- minSdk: 24
- targetSdk: 36
- compileSdk: 36.1
- versionCode: 1
- versionName: 1.0
- Java source/target: 11
- CI JDK: 17
