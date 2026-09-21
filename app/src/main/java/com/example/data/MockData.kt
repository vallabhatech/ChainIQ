package com.example.data

object MockData {

  val defaultIncident = IncidentAlert(
    id = "INC-2026-089",
    title = "Supplier A Disruption",
    supplierName = "Supplier A",
    componentName = "Critical Component X",
    durationDays = 15,
    daysToShortage = 6,
    severity = "HIGH",
    affectedPlants = 2,
    ordersAtRisk = 17,
    affectedCustomers = 6,
    timestamp = "14 mins ago"
  )

  val recoveryOptions = listOf(
    RecoveryOption(
      id = "OPT-A",
      title = "OPTION A",
      subtitle = "Local Supplier + Truck",
      costLabel = "Medium",
      costLevel = 2,
      recoveryDays = 5,
      riskLabel = "Low",
      carbonLabel = "Low",
      capacityPct = 85,
      statusBadge = "Strong option",
      isRecommended = true,
      description = "Diverts procurement to regional partner in Nuremberg via dedicated electric-hybrid freight.",
      keyAdvantages = listOf(
        "Prevents predicted shortage in 6 days",
        "Low disruption risk with verified capacity",
        "Fast enough for current inventory",
        "Moderate additional cost (+$8,500)",
        "Lower carbon impact (-38% vs Air)"
      )
    ),
    RecoveryOption(
      id = "OPT-B",
      title = "OPTION B",
      subtitle = "Overseas Supplier + Ship",
      costLabel = "Low",
      costLevel = 1,
      recoveryDays = 15,
      riskLabel = "Medium",
      carbonLabel = "Medium",
      capacityPct = 100,
      statusBadge = "Cost-Effective",
      isRecommended = false,
      description = "Consolidates deep-water container freight from Busan terminal to Rotterdam.",
      keyAdvantages = listOf(
        "Lowest procurement surge cost",
        "Full bulk capacity available",
        "Risk of stockout if port transit delays occur"
      )
    ),
    RecoveryOption(
      id = "OPT-C",
      title = "OPTION C",
      subtitle = "Emergency Supplier + Air",
      costLabel = "High",
      costLevel = 3,
      recoveryDays = 2,
      riskLabel = "Very Low",
      carbonLabel = "High",
      capacityPct = 60,
      statusBadge = "Fastest",
      isRecommended = false,
      description = "Expedited charter air cargo from Frankfurt hub directly to plant staging site.",
      keyAdvantages = listOf(
        "Fastest possible delivery (48h turnaround)",
        "Near-zero inventory depletion risk",
        "Substantial premium cost (+65%)"
      )
    )
  )

  fun getTimelineSteps(durationDays: Int): List<TimelineStep> {
    return when {
      durationDays <= 7 -> listOf(
        TimelineStep("TODAY", 0, "Disruption detected", "Supplier A halted outbound shipments.", "INFO"),
        TimelineStep("DAY 2", 2, "Buffer depletion", "Safety buffer drops to 75% at Plant 02.", "INFO"),
        TimelineStep("DAY 5", 5, "Material shortage risk", "Warning threshold reached.", "WARNING"),
        TimelineStep("DAY 7", 7, "Controlled Recovery", "Alternative delivery arrives.", "INFO")
      )
      durationDays <= 20 -> listOf(
        TimelineStep("TODAY", 0, "Disruption detected", "Supplier A halted outbound shipments.", "INFO"),
        TimelineStep("DAY 2", 2, "Inventory begins declining", "Plant 02 consumption continues.", "INFO"),
        TimelineStep("DAY 6", 6, "Material shortage risk", "Safety stock fully depleted.", "WARNING", "⚠ Critical Threshold"),
        TimelineStep("DAY 8", 8, "Production impact", "Assembly lines at Plant 01 & 02 idle.", "CRITICAL", "🏭 Plant Idle"),
        TimelineStep("DAY 11", 11, "Orders at risk", "17 customer delivery commitments slip.", "CRITICAL", "📦 17 Orders"),
        TimelineStep("DAY 15", 15, "Major service impact", "SLA violation penalties initiated.", "CRITICAL", "🔴 Severe")
      )
      else -> listOf(
        TimelineStep("TODAY", 0, "Disruption detected", "Supplier A halted outbound shipments.", "INFO"),
        TimelineStep("DAY 4", 4, "Safety stock exhausted", "Critical stockout at Plant 02.", "WARNING"),
        TimelineStep("DAY 8", 8, "Production shutdown", "Multi-line stoppage across both plants.", "CRITICAL"),
        TimelineStep("DAY 15", 15, "Cascading disruption", "31 OEM customer commitments breached.", "CRITICAL"),
        TimelineStep("DAY 22", 22, "Tier-2 vendor defaults", "Secondary assembly delays ripple outward.", "CRITICAL"),
        TimelineStep("DAY 30", 30, "Severe financial penalty", "Cumulative loss estimated at $2.4M.", "CRITICAL")
      )
    }
  }

  val networkNodes = listOf(
    SupplyNode("sup_a", "Supplier A", NodeCategory.SUPPLIER, NodeStatus.DISRUPTED, 0.22f, 0.25f, 4, 2, "HIGH", "Stuttgart, DE", 0, "Critical Component X vendor - 15d stoppage"),
    SupplyNode("sup_b", "Supplier B", NodeCategory.SUPPLIER, NodeStatus.WARNING, 0.15f, 0.65f, 6, 3, "MEDIUM", "Nuremberg, DE", 18, "Tier-2 raw material dependency detected"),
    SupplyNode("sup_c", "Supplier C", NodeCategory.SUPPLIER, NodeStatus.HEALTHY, 0.35f, 0.82f, 8, 4, "LOW", "Linz, AT", 25, "Certified alternate supplier for Tier-1 fasteners"),
    SupplyNode("plt_01", "Plant 01", NodeCategory.PLANT, NodeStatus.HEALTHY, 0.50f, 0.28f, 12, 1, "LOW", "Munich, DE", 14, "Primary EV motor powertrain assembly"),
    SupplyNode("plt_02", "Plant 02", NodeCategory.PLANT, NodeStatus.WARNING, 0.52f, 0.60f, 9, 2, "HIGH", "Leipzig, DE", 6, "Inverter module assembly - projected shortage in 6d"),
    SupplyNode("wh_b", "Warehouse B", NodeCategory.WAREHOUSE, NodeStatus.WARNING, 0.72f, 0.42f, 22, 2, "MEDIUM", "Hamburg, DE", 8, "Central European distribution logistics hub"),
    SupplyNode("ord_17", "17 Active Orders", NodeCategory.ORDER, NodeStatus.WARNING, 0.88f, 0.30f, 17, 1, "HIGH", "Global Delivery", 5, "Tier-1 OEM vehicle build commitments"),
    SupplyNode("cust_06", "6 Key Clients", NodeCategory.CUSTOMER, NodeStatus.HEALTHY, 0.86f, 0.72f, 6, 1, "MEDIUM", "EU Auto OEMs", 0, "High-priority contract partners")
  )

  val networkLinks = listOf(
    SupplyLink("sup_a", "plt_01", isDisrupted = true, label = "Delayed"),
    SupplyLink("sup_a", "plt_02", isDisrupted = true, label = "Blocked"),
    SupplyLink("sup_b", "plt_02", isDisrupted = false, label = "Alternate"),
    SupplyLink("sup_c", "plt_01", isDisrupted = false, label = "Active"),
    SupplyLink("plt_01", "wh_b", isDisrupted = false, label = "Normal"),
    SupplyLink("plt_02", "wh_b", isDisrupted = true, label = "At Risk"),
    SupplyLink("wh_b", "ord_17", isDisrupted = true, label = "Shortage"),
    SupplyLink("ord_17", "cust_06", isDisrupted = true, label = "SLA Risk")
  )

  val initialBusinessRules = listOf(
    BusinessRule("R1", "Never select a high-risk supplier.", true, "Risk Management"),
    BusinessRule("R2", "Use emergency air freight only when production is at risk within 48 hours.", true, "Logistics & Cost"),
    BusinessRule("R3", "Maintain at least 30% secondary supplier capacity.", true, "Resilience & Sourcing"),
    BusinessRule("R4", "Cap single-order recovery cost surge to 25% without executive sign-off.", false, "Financial Governance")
  )

  val initialChatMessages = listOf(
    ChatMessage(
      id = "msg_1",
      sender = "user",
      text = "Why not Supplier C?",
      timestamp = "10:32 AM"
    ),
    ChatMessage(
      id = "msg_2",
      sender = "ai",
      text = "Supplier C is cheaper, but its expected delivery time is longer. Based on current inventory, that could increase shortage risk.",
      timestamp = "10:32 AM"
    ),
    ChatMessage(
      id = "msg_3",
      sender = "user",
      text = "What if the disruption lasts 30 days?",
      timestamp = "10:33 AM"
    ),
    ChatMessage(
      id = "msg_4",
      sender = "ai",
      text = "Running scenario simulation…\n\n30-day disruption scenario:\n• Production impact: HIGH\n• Orders at risk: 31\n• Recovery cost: +18%\n• Suggested strategy: Multi-source recovery (Shift 60% to Supplier B + 40% air freight)",
      timestamp = "10:33 AM",
      scenarioDetails = ScenarioDetails(
        durationDays = 30,
        productionImpact = "HIGH",
        ordersAtRisk = 31,
        recoveryCostDelta = "+18%",
        suggestedStrategy = "Multi-source recovery"
      )
    )
  )
}
