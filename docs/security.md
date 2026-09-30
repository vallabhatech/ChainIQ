# Security

## Secrets

Use the configured Secrets Gradle Plugin and environment files for sensitive values.

Never commit:

- API keys
- passwords
- signing credentials
- service-account files
- production keystores

## Signing

Release signing uses environment variables. Debug signing is separate and must not be treated as a production identity.

## Authentication

Firebase Auth and Google Credential Manager dependencies are commented out. No active authentication flow was identified.

## Backend and data

No active application-specific backend or persistent database implementation was identified in the inspected source tree.

Do not connect real sensitive supply-chain records to this prototype without implementing an appropriate authenticated backend, authorization, validation, secure storage and audit controls.

## Firebase App Check

App Check dependencies are present for reCAPTCHA and debug, but production enforcement should not be claimed without active Firebase configuration and runtime verification.

## Production hardening

Before production use, establish:

- authenticated access
- authorization
- secure backend architecture
- input validation
- sensitive-data protection
- secret rotation
- secure logging/redaction
- dependency scanning
- release signing controls
- data retention rules
- incident-response procedures
