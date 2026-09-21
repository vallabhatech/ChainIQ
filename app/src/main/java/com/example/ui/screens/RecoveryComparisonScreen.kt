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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PrimaryActionButton
import com.example.ui.components.StatusChip
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
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

@Composable
fun RecoveryComparisonScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onViewRecommendation: () -> Unit
) {
  val selectedPriorities by viewModel.selectedPriorities.collectAsState()

  val priorityOptions = listOf("Low Cost", "Fast Recovery", "Low Risk", "Low Carbon")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyBackground)
      .padding(horizontal = 20.dp)
      .verticalScroll(rememberScrollState())
  ) {
    Spacer(modifier = Modifier.height(16.dp))

    // Header
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      IconButton(onClick = onNavigateBack) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back",
          tint = TextPrimary
        )
      }
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "Compare Plans",
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "Compare Recovery Plans",
      color = TextPrimary,
      fontSize = 24.sp,
      fontWeight = FontWeight.ExtraBold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Evaluate trade-offs between speed, cost, and resilience risk",
      color = TextSecondary,
      fontSize = 13.sp
    )

    Spacer(modifier = Modifier.height(20.dp))

    // YOUR PRIORITIES
    Text(
      text = "YOUR PRIORITIES",
      color = TextSecondary,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      priorityOptions.forEach { prio ->
        val isSelected = selectedPriorities.contains(prio)
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) SapBlue.copy(alpha = 0.25f) else NavyCard)
            .border(
              1.dp,
              if (isSelected) CyanAccent else NavyBorder,
              RoundedCornerShape(12.dp)
            )
            .clickable { viewModel.togglePriority(prio) }
            .padding(vertical = 10.dp, horizontal = 4.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = prio,
            color = if (isSelected) CyanAccent else TextSecondary,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Comparison Matrix Table
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(NavyCard)
        .border(1.dp, NavyBorder, RoundedCornerShape(18.dp))
        .padding(14.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Table Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("CRITERIA", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.3f))
          Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("PLAN A", color = CyanAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("Truck", color = TextMuted, fontSize = 10.sp)
          }
          Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("PLAN B", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("Ship", color = TextMuted, fontSize = 10.sp)
          }
          Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("PLAN C", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("Air", color = TextMuted, fontSize = 10.sp)
          }
        }

        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(NavyBorder))

        // Matrix Rows
        ComparisonRow(
          label = "Cost",
          valA = "Medium", valB = "Low", valC = "High",
          colorA = WarningAmber, colorB = HealthyGreen, colorC = DisruptionRed,
          isEmphasized = selectedPriorities.contains("Low Cost")
        )

        ComparisonRow(
          label = "Delivery Time",
          valA = "5 days", valB = "15 days", valC = "2 days",
          colorA = HealthyGreen, colorB = DisruptionRed, colorC = HealthyGreen,
          isEmphasized = selectedPriorities.contains("Fast Recovery")
        )

        ComparisonRow(
          label = "Risk",
          valA = "Low", valB = "Medium", valC = "Very Low",
          colorA = HealthyGreen, colorB = WarningAmber, colorC = HealthyGreen,
          isEmphasized = selectedPriorities.contains("Low Risk")
        )

        ComparisonRow(
          label = "Carbon",
          valA = "Low", valB = "Medium", valC = "High",
          colorA = HealthyGreen, colorB = WarningAmber, colorC = DisruptionRed,
          isEmphasized = selectedPriorities.contains("Low Carbon")
        )

        ComparisonRow(
          label = "Capacity",
          valA = "85%", valB = "100%", valC = "60%",
          colorA = HealthyGreen, colorB = HealthyGreen, colorC = WarningAmber
        )

        ComparisonRow(
          label = "Recovery Speed",
          valA = "Fast", valB = "Slow", valC = "Instant",
          colorA = HealthyGreen, colorB = WarningAmber, colorC = HealthyGreen
        )
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Why these trade-offs matter explanation
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(NavyCardElevated)
        .border(1.dp, SapBlue.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
        .padding(16.dp)
    ) {
      Row(verticalAlignment = Alignment.Top) {
        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Why these trade-offs matter",
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "“There is no single perfect plan. ResiliGraph compares the consequences of each choice.”",
            color = TextSecondary,
            fontSize = 12.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            lineHeight = 16.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    PrimaryActionButton(
      text = "View Recommendation",
      onClick = onViewRecommendation,
      icon = Icons.Default.ArrowForward,
      color = SapBlue,
      modifier = Modifier.testTag("view_recommendation_button")
    )

    Spacer(modifier = Modifier.height(30.dp))
  }
}

@Composable
private fun ComparisonRow(
  label: String,
  valA: String,
  valB: String,
  valC: String,
  colorA: Color,
  colorB: Color,
  colorC: Color,
  isEmphasized: Boolean = false
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .background(if (isEmphasized) CyanAccent.copy(alpha = 0.08f) else Color.Transparent)
      .padding(vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      color = if (isEmphasized) CyanAccent else TextSecondary,
      fontSize = 12.sp,
      fontWeight = if (isEmphasized) FontWeight.Bold else FontWeight.Normal,
      modifier = Modifier.weight(1.3f)
    )
    Text(
      text = valA,
      color = colorA,
      fontSize = 12.sp,
      fontWeight = FontWeight.SemiBold,
      textAlign = TextAlign.Center,
      modifier = Modifier.weight(1f)
    )
    Text(
      text = valB,
      color = colorB,
      fontSize = 12.sp,
      fontWeight = FontWeight.Normal,
      textAlign = TextAlign.Center,
      modifier = Modifier.weight(1f)
    )
    Text(
      text = valC,
      color = colorC,
      fontSize = 12.sp,
      fontWeight = FontWeight.Normal,
      textAlign = TextAlign.Center,
      modifier = Modifier.weight(1f)
    )
  }
}
