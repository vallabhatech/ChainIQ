# Workflows

## Disruption reporting

~~~mermaid
sequenceDiagram
    actor User
    participant Screen as ReportDisruptionScreen
    participant VM as ResiliGraphViewModel

    User->>Screen: Enter disruption
    Screen->>VM: updateInputText
    VM-->>Screen: Updated StateFlow
~~~

Voice input is simulated by startSimulatedVoiceRecording. It clears input, waits about 2.2 seconds, then inserts the demo incident text.

Document upload is simulated by simulateDocumentUpload. It assigns supplier_a_notice_letter.pdf and predefined incident text. No real document parser is implemented in this method.

## Incident analysis

startAnalysisSimulation advances analysis state through four stages with delays of approximately 700 ms, 800 ms and 900 ms.

This is UI simulation, not a live analysis pipeline.

## Recovery simulation

The duration is stored by setDurationDays.

MockData.getTimelineSteps returns different timeline sets for:

- up to 7 days
- up to 20 days
- longer scenarios

## Recovery selection

The ViewModel stores the selected recovery option ID.

Demo options include local supplier + truck, overseas supplier + ship, and emergency supplier + air.

## Supply network

~~~mermaid
flowchart LR
    Supplier --> Plant
    Plant --> Warehouse
    Warehouse --> Orders
    Orders --> Customers
~~~

MockData supplies the nodes and links. Some links are marked disrupted, blocked, alternate, at-risk or active.

## Business rules

Rules are held in ViewModel state.

Users can toggle rules and add rules. The changes are not persisted.

## Challenge AI / chat

~~~mermaid
sequenceDiagram
    actor User
    participant UI
    participant VM

    User->>UI: Submit prompt
    UI->>VM: sendUserChatMessage
    VM->>VM: Add user message
    VM->>VM: Wait
    VM->>VM: Keyword match
    VM->>VM: Add deterministic response
    VM-->>UI: Updated chat state
~~~

## Approval

Approval begins in PENDING state. approvePlan changes it to APPROVED and adjusts the local resilience score.

setApprovalState can directly change the approval state.

## Demo reset

loadDemoScenario restores the primary 15-day Supplier A scenario, Option A, score 82, pending approval, default chat and no uploaded document.
