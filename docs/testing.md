# Testing

## Test layers

The repository includes:

- JUnit
- AndroidX JUnit
- Espresso
- Compose UI Test
- Robolectric
- Kotlin coroutines test
- Roborazzi

## Locations

Local tests:

app/src/test/

Instrumented tests:

app/src/androidTest/

Existing test examples include:

- ExampleUnitTest.kt
- ExampleRobolectricTest.kt
- GreetingScreenshotTest.kt
- ExampleInstrumentedTest.kt

A screenshot asset exists at app/src/test/screenshots/greeting.png.

## Commands

~~~bash
gradle test
gradle connectedAndroidTest
gradle assembleDebug
~~~

Instrumented tests require a connected device/emulator.

## Adding tests

For a new feature:

1. test ViewModel state transitions where practical
2. test important Compose behavior
3. use instrumented tests for Android-runtime behavior
4. add/update screenshot tests for valuable visual regression coverage
5. keep demo data deterministic

The presence of test infrastructure does not imply complete business-logic coverage.
