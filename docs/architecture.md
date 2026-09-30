# Architecture

## High-level design

~~~mermaid
flowchart TD
    User[User] --> Activity[MainActivity]
    Activity --> Navigation[Navigation Compose]
    Navigation --> Screens[Compose Screens]
    Screens --> VM[ResiliGraphViewModel]
    VM --> Mock[MockData]
    VM --> Models[Domain Models]
    VM --> State[StateFlow]
    State --> Screens
    Screens --> Components[Shared Components]
    Components --> Theme[Material 3 Theme]
~~~

## Application shell

MainActivity.kt:

- enables edge-to-edge rendering
- applies MyApplicationTheme
- creates the NavController
- creates the shared ResiliGraphViewModel
- registers routes and screens
- defines bottom navigation

## Navigation

Routes are centralized in ui/navigation/Routes.kt.

## UI

Feature screens live in ui/screens.

Shared components include:

- CommonComponents.kt
- NetworkGraphCanvas.kt

Theme files include:

- Color.kt
- Theme.kt
- Type.kt

## State

ResiliGraphViewModel is the central state holder.

It exposes StateFlow values for:

- incident
- input text
- recording state
- uploaded document
- analysis step
- duration
- recovery options
- selected recovery option
- priorities
- network nodes
- selected node
- resilience score
- business rules
- chat messages
- approval state
- selected industry

## State flow

~~~mermaid
sequenceDiagram
    actor User
    participant UI as Compose Screen
    participant VM as ViewModel
    participant Data as MockData

    User->>UI: Interact
    UI->>VM: Event method
    VM->>Data: Read demo data
    VM->>VM: Update MutableStateFlow
    VM-->>UI: StateFlow update
    UI-->>User: Recompose
~~~

## Recovery flow

~~~mermaid
flowchart LR
    Incident[Incident] --> Impact[Impact]
    Impact --> Simulation[Recovery Simulation]
    Simulation --> Comparison[Recovery Comparison]
    Comparison --> Recommendation[Recommendation]
    Recommendation --> Approval[Approval]
    Approval --> Success[Recovery Success]
~~~

## Supply graph

SupplyNode and SupplyLink represent the demo network. The graph contains suppliers, plants, warehouse, orders and customers. NetworkGraphCanvas renders the graph visualization.

## AI/chat

The current sendUserChatMessage implementation is local and deterministic:

1. append user message
2. wait about 900 ms
3. inspect prompt keywords
4. create a predefined response
5. append the response

It is not a live LLM request path in the inspected implementation.

## Persistence and backend

No active application-specific database or backend service was identified in the inspected source tree. Room, Retrofit, OkHttp and Moshi are declared dependencies, but they are not by themselves active architecture layers.

## Authentication

Firebase Auth and Google Credential Manager dependencies are commented out in the current app dependency configuration. No active authentication flow was identified.
