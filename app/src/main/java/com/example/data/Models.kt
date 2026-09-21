package com.example.data

enum class NodeStatus {
  HEALTHY,
  WARNING,
  DISRUPTED
}

enum class NodeCategory {
  SUPPLIER,
  PLANT,
  WAREHOUSE,
  ORDER,
  CUSTOMER,
  TRANSPORT
}

data class SupplyNode(
  val id: String,
  val name: String,
  val category: NodeCategory,
  val status: NodeStatus,
  val x: Float, // 0f..1f
  val y: Float, // 0f..1f
  val materialsCount: Int = 1,
  val connectedPlantsCount: Int = 1,
  val riskLevel: String = "LOW",
  val location: String = "Munich, Germany",
  val inventoryDays: Int = 14,
  val description: String = "Core manufacturing partner"
)

data class SupplyLink(
  val fromId: String,
  val toId: String,
  val isDisrupted: Boolean = false,
  val label: String = ""
)

data class IncidentAlert(
  val id: String,
  val title: String,
  val supplierName: String,
  val componentName: String,
  val durationDays: Int,
  val daysToShortage: Int,
  val severity: String,
  val affectedPlants: Int,
  val ordersAtRisk: Int,
  val affectedCustomers: Int,
  val timestamp: String
)

data class RecoveryOption(
  val id: String,
  val title: String,
  val subtitle: String,
  val costLabel: String,
  val costLevel: Int, // 1: Low, 2: Med, 3: High
  val recoveryDays: Int,
  val riskLabel: String,
  val carbonLabel: String,
  val capacityPct: Int,
  val statusBadge: String? = null,
  val isRecommended: Boolean = false,
  val description: String,
  val keyAdvantages: List<String> = emptyList()
)

data class TimelineStep(
  val dayLabel: String,
  val dayNumber: Int,
  val title: String,
  val description: String,
  val severity: String, // INFO, WARNING, CRITICAL
  val badge: String = ""
)

data class BusinessRule(
  val id: String,
  val ruleText: String,
  val isEnabled: Boolean,
  val category: String = "Sourcing"
)

data class ScenarioDetails(
  val durationDays: Int,
  val productionImpact: String,
  val ordersAtRisk: Int,
  val recoveryCostDelta: String,
  val suggestedStrategy: String
)

data class ChatMessage(
  val id: String,
  val sender: String, // "user" or "ai"
  val text: String,
  val timestamp: String,
  val scenarioDetails: ScenarioDetails? = null
)
