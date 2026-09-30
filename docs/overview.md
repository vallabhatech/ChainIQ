# Overview

## Identity

- Repository: ChainIQ
- Android project: ResiliGraph
- Root project name: ResiliGraph
- Application ID: com.aistudio.resiligraph.vckzpq
- Main package: com.example

## Purpose

ResiliGraph is an Android prototype for navigating supply-chain crises. It represents a disruption, calculates/demo-models its downstream impact, compares recovery choices, visualizes supply dependencies, evaluates resilience, applies policy rules, and moves a plan through approval.

## Main screens

The route definitions currently include:

- Splash
- Home
- Report Disruption
- Incident Analysis
- Impact Radius
- Impact Time Machine
- Recovery Simulator
- Recovery Comparison
- Recommendation
- Challenge AI
- Hidden Dependency
- Supply Graph
- Resilience Score
- Policy Brain
- Approval
- Recovery Success
- Settings

## Core data

Domain models live in app/src/main/java/com/example/data/Models.kt.

Demo state lives in app/src/main/java/com/example/data/MockData.kt.

The default incident is a 15-day Supplier A disruption affecting Critical Component X.

## State

ResiliGraphViewModel owns the interactive prototype state using MutableStateFlow and exposes StateFlow values for incident, input, upload state, analysis progress, duration, recovery options, priorities, graph nodes, resilience score, business rules, chat messages, approval state and industry.

## Important boundary

The current implementation is primarily local simulation.

Firebase AI/Gemini, Retrofit, OkHttp, Moshi, Room and authentication-related dependencies exist in Gradle, but dependencies alone are not evidence of an active integration.

The inspected application source currently does not establish:

- a production backend
- persistent database storage
- active authentication
- live ERP integration
- live Gemini generation
- real microphone capture
- real document ingestion

Do not document these as implemented until the runtime code supports them.
