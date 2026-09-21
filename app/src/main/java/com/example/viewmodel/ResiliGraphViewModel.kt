package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BusinessRule
import com.example.data.ChatMessage
import com.example.data.IncidentAlert
import com.example.data.MockData
import com.example.data.RecoveryOption
import com.example.data.ScenarioDetails
import com.example.data.SupplyNode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResiliGraphViewModel : ViewModel() {

  private val _incident = MutableStateFlow(MockData.defaultIncident)
  val incident: StateFlow<IncidentAlert> = _incident.asStateFlow()

  private val _inputText = MutableStateFlow("Supplier A stopped delivery for 15 days.")
  val inputText: StateFlow<String> = _inputText.asStateFlow()

  private val _isRecordingVoice = MutableStateFlow(false)
  val isRecordingVoice: StateFlow<Boolean> = _isRecordingVoice.asStateFlow()

  private val _uploadedDocument = MutableStateFlow<String?>(null)
  val uploadedDocument: StateFlow<String?> = _uploadedDocument.asStateFlow()

  private val _analysisStep = MutableStateFlow(4)
  val analysisStep: StateFlow<Int> = _analysisStep.asStateFlow()

  private val _durationDays = MutableStateFlow(15)
  val durationDays: StateFlow<Int> = _durationDays.asStateFlow()

  private val _recoveryOptions = MutableStateFlow(MockData.recoveryOptions)
  val recoveryOptions: StateFlow<List<RecoveryOption>> = _recoveryOptions.asStateFlow()

  private val _selectedOptionId = MutableStateFlow("OPT-A")
  val selectedOptionId: StateFlow<String> = _selectedOptionId.asStateFlow()

  private val _selectedPriorities = MutableStateFlow(setOf("Fast Recovery", "Low Risk"))
  val selectedPriorities: StateFlow<Set<String>> = _selectedPriorities.asStateFlow()

  private val _nodes = MutableStateFlow(MockData.networkNodes)
  val nodes: StateFlow<List<SupplyNode>> = _nodes.asStateFlow()

  private val _selectedNode = MutableStateFlow<SupplyNode?>(null)
  val selectedNode: StateFlow<SupplyNode?> = _selectedNode.asStateFlow()

  private val _resilienceScore = MutableStateFlow(82)
  val resilienceScore: StateFlow<Int> = _resilienceScore.asStateFlow()

  private val _scoreSimulated = MutableStateFlow(82)
  val scoreSimulated: StateFlow<Int> = _scoreSimulated.asStateFlow()

  private val _businessRules = MutableStateFlow(MockData.initialBusinessRules)
  val businessRules: StateFlow<List<BusinessRule>> = _businessRules.asStateFlow()

  private val _chatMessages = MutableStateFlow(MockData.initialChatMessages)
  val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

  private val _approvalState = MutableStateFlow("PENDING") // PENDING, APPROVED, REVIEW, REJECTED
  val approvalState: StateFlow<String> = _approvalState.asStateFlow()

  private val _selectedIndustry = MutableStateFlow("Automotive")
  val selectedIndustry: StateFlow<String> = _selectedIndustry.asStateFlow()

  fun updateInputText(newText: String) {
    _inputText.value = newText
  }

  fun startSimulatedVoiceRecording() {
    viewModelScope.launch {
      _isRecordingVoice.value = true
      _inputText.value = ""
      delay(2200)
      _isRecordingVoice.value = false
      _inputText.value = "Supplier A stopped delivery for 15 days."
    }
  }

  fun simulateDocumentUpload() {
    _uploadedDocument.value = "supplier_a_notice_letter.pdf"
    _inputText.value = "Supplier A stopped delivery for 15 days. Critical Component X impacted across plants."
  }

  fun clearUploadedDocument() {
    _uploadedDocument.value = null
  }

  fun startAnalysisSimulation() {
    viewModelScope.launch {
      _analysisStep.value = 1
      delay(700)
      _analysisStep.value = 2
      delay(800)
      _analysisStep.value = 3
      delay(900)
      _analysisStep.value = 4
    }
  }

  fun setDurationDays(days: Int) {
    _durationDays.value = days
  }

  fun selectRecoveryOption(optionId: String) {
    _selectedOptionId.value = optionId
  }

  fun togglePriority(priority: String) {
    val current = _selectedPriorities.value.toMutableSet()
    if (current.contains(priority)) {
      if (current.size > 1) current.remove(priority)
    } else {
      current.add(priority)
    }
    _selectedPriorities.value = current
  }

  fun selectNode(node: SupplyNode?) {
    _selectedNode.value = node
  }

  fun setSimulatedResilienceScore(score: Int) {
    _scoreSimulated.value = score
  }

  fun toggleBusinessRule(ruleId: String) {
    _businessRules.value = _businessRules.value.map { rule ->
      if (rule.id == ruleId) rule.copy(isEnabled = !rule.isEnabled) else rule
    }
  }

  fun addBusinessRule(text: String, category: String = "Policy") {
    val newRule = BusinessRule(
      id = "R${System.currentTimeMillis() % 10000}",
      ruleText = text,
      isEnabled = true,
      category = category
    )
    _businessRules.value = _businessRules.value + newRule
  }

  fun sendUserChatMessage(prompt: String) {
    if (prompt.isBlank()) return
    val userMsg = ChatMessage(
      id = "msg_${System.currentTimeMillis()}",
      sender = "user",
      text = prompt,
      timestamp = "Just now"
    )
    _chatMessages.value = _chatMessages.value + userMsg

    viewModelScope.launch {
      delay(900)
      val aiReply = when {
        prompt.contains("Supplier C", ignoreCase = true) || prompt.contains("Option C", ignoreCase = true) -> {
          ChatMessage(
            id = "ai_${System.currentTimeMillis()}",
            sender = "ai",
            text = "Supplier C is 18% cheaper, but its transit time is 15 days via sea. Based on Plant 02's current 6-day stock, selecting Supplier C triggers an 8-day blackout window.",
            timestamp = "Just now"
          )
        }
        prompt.contains("30", ignoreCase = true) || prompt.contains("longer", ignoreCase = true) -> {
          ChatMessage(
            id = "ai_${System.currentTimeMillis()}",
            sender = "ai",
            text = "Running extended 30-day projection:\n• Plant 01 & 02 production lines halted at Day 8\n• 31 tier-1 OEM orders delayed\n• Recommendation: Activate dual-sourcing (Supplier B 60% + Spot Air 40%).",
            timestamp = "Just now",
            scenarioDetails = ScenarioDetails(
              durationDays = 30,
              productionImpact = "HIGH",
              ordersAtRisk = 31,
              recoveryCostDelta = "+24%",
              suggestedStrategy = "Dual-Source Air/Rail Buffer"
            )
          )
        }
        prompt.contains("cost", ignoreCase = true) || prompt.contains("budget", ignoreCase = true) -> {
          ChatMessage(
            id = "ai_${System.currentTimeMillis()}",
            sender = "ai",
            text = "Plan A costs an estimated +$8,500 (+6.2% freight premium), avoiding an estimated $340,000 idle plant downtime penalty.",
            timestamp = "Just now"
          )
        }
        else -> {
          ChatMessage(
            id = "ai_${System.currentTimeMillis()}",
            sender = "ai",
            text = "ResiliGraph evaluated this query against current active MRP buffer levels and transportation capacity: Plan A maintains the optimal balance between speed (5d) and minimal cost disruption.",
            timestamp = "Just now"
          )
        }
      }
      _chatMessages.value = _chatMessages.value + aiReply
    }
  }

  fun approvePlan() {
    _approvalState.value = "APPROVED"
    _resilienceScore.value = 78
    _scoreSimulated.value = 78
  }

  fun setApprovalState(state: String) {
    _approvalState.value = state
  }

  fun setIndustry(industry: String) {
    _selectedIndustry.value = industry
  }

  fun loadDemoScenario() {
    _incident.value = MockData.defaultIncident
    _inputText.value = "Supplier A stopped delivery for 15 days."
    _durationDays.value = 15
    _selectedOptionId.value = "OPT-A"
    _analysisStep.value = 4
    _resilienceScore.value = 82
    _scoreSimulated.value = 82
    _approvalState.value = "PENDING"
    _chatMessages.value = MockData.initialChatMessages
    _uploadedDocument.value = null
  }
}
