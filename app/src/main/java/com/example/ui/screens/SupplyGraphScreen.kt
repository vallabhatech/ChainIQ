package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MockData
import com.example.data.NodeCategory
import com.example.data.NodeStatus
import com.example.data.SupplyNode
import com.example.ui.components.InteractiveNetworkGraph
import com.example.ui.components.StatusChip
import com.example.ui.navigation.Routes
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.DisruptionRed
import com.example.ui.theme.HealthyGreen
import com.example.ui.theme.NavyBackground
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.SapBlue
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber
import com.example.viewmodel.ResiliGraphViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplyGraphScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onInvestigateNode: (SupplyNode) -> Unit
) {
  val allNodes by viewModel.nodes.collectAsState()
  val links = MockData.networkLinks
  val selectedNode by viewModel.selectedNode.collectAsState()
  var filterCategory by remember { mutableStateOf("ALL") }

  val filteredNodes = when (filterCategory) {
    "DISRUPTED" -> allNodes.filter { it.status == NodeStatus.DISRUPTED || it.status == NodeStatus.WARNING }
    "PLANTS" -> allNodes.filter { it.category == NodeCategory.PLANT }
    "SUPPLIERS" -> allNodes.filter { it.category == NodeCategory.SUPPLIER }
    else -> allNodes
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyBackground)
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onNavigateBack) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
          Text("Supply Network", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
          Text("Interactive E2E Graph Topology", color = CyanAccent, fontSize = 11.sp)
        }
      }
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(NavyCard)
            .border(1.dp, NavyBorder, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text("8 Nodes", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // Filter Chips Row
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      FilterTabChip("ALL", filterCategory == "ALL") { filterCategory = "ALL" }
      FilterTabChip("DISRUPTED", filterCategory == "DISRUPTED", DisruptionRed) { filterCategory = "DISRUPTED" }
      FilterTabChip("PLANTS", filterCategory == "PLANTS") { filterCategory = "PLANTS" }
      FilterTabChip("SUPPLIERS", filterCategory == "SUPPLIERS") { filterCategory = "SUPPLIERS" }
    }

    // Interactive Graph Canvas Area
    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(12.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(NavyCard)
        .border(1.dp, NavyBorder, RoundedCornerShape(20.dp))
    ) {
      InteractiveNetworkGraph(
        nodes = filteredNodes,
        links = links,
        selectedNodeId = selectedNode?.id,
        onNodeTap = { node -> viewModel.selectNode(node) },
        modifier = Modifier.fillMaxSize()
      )

      // Legend
      Row(
        modifier = Modifier
          .align(Alignment.TopStart)
          .padding(12.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(NavyCardElevated.copy(alpha = 0.85f))
          .border(1.dp, NavyBorder, RoundedCornerShape(10.dp))
          .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        LegendDot(HealthyGreen, "Healthy")
        LegendDot(WarningAmber, "Warning")
        LegendDot(DisruptionRed, "Disrupted")
      }

      Text(
        text = "Tap any node for details",
        color = TextMuted,
        fontSize = 11.sp,
        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp)
      )
    }

    // Selected Node Bottom Sheet / Card
    selectedNode?.let { node ->
      val statusColor = when (node.status) {
        NodeStatus.DISRUPTED -> DisruptionRed
        NodeStatus.WARNING -> WarningAmber
        NodeStatus.HEALTHY -> HealthyGreen
      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
          .background(NavyCardElevated)
          .border(1.dp, statusColor.copy(alpha = 0.5f), RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
          .padding(18.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = node.name,
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${node.category.name} • ${node.location}",
                color = TextSecondary,
                fontSize = 12.sp
              )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
              StatusChip(text = node.status.name, color = statusColor)
              Spacer(modifier = Modifier.width(6.dp))
              IconButton(onClick = { viewModel.selectNode(null) }, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted, modifier = Modifier.size(16.dp))
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            InfoCol("Materials", "${node.materialsCount}")
            InfoCol("Connected Plants", "${node.connectedPlantsCount}")
            InfoCol("Risk", node.riskLevel, statusColor)
            InfoCol("Coverage", "${node.inventoryDays} days")
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = { onInvestigateNode(node) },
            modifier = Modifier.fillMaxWidth().height(46.dp).testTag("investigate_node_button"),
            colors = ButtonDefaults.buttonColors(containerColor = if (node.status == NodeStatus.DISRUPTED) DisruptionRed else SapBlue),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text("Investigate ${node.name}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
          }
        }
      }
    }

    if (selectedNode == null) {
      Spacer(modifier = Modifier.height(70.dp))
    }
  }
}

@Composable
private fun LegendDot(color: Color, label: String) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
    Spacer(modifier = Modifier.width(4.dp))
    Text(label, color = TextSecondary, fontSize = 10.sp)
  }
}

@Composable
private fun FilterTabChip(label: String, isSelected: Boolean, activeColor: Color = CyanAccent, onClick: () -> Unit) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(12.dp))
      .background(if (isSelected) activeColor.copy(alpha = 0.2f) else NavyCard)
      .border(1.dp, if (isSelected) activeColor else NavyBorder, RoundedCornerShape(12.dp))
      .clickable { onClick() }
      .padding(horizontal = 10.dp, vertical = 6.dp)
  ) {
    Text(label, color = if (isSelected) activeColor else TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
  }
}

@Composable
private fun InfoCol(label: String, value: String, valueColor: Color = TextPrimary) {
  Column {
    Text(label, color = TextMuted, fontSize = 10.sp)
    Spacer(modifier = Modifier.height(2.dp))
    Text(value, color = valueColor, fontSize = 13.sp, fontWeight = FontWeight.Bold)
  }
}
