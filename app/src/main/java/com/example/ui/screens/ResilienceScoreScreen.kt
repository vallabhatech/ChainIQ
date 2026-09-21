package com.example.ui.screens

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PrimaryActionButton
import com.example.ui.components.StatusChip
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.DisruptionRed
import com.example.ui.theme.HealthyGreen
import com.example.ui.theme.NavyBackground
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.SapBlue
import com.example.ui.theme.SapBlueLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber
import com.example.viewmodel.ResiliGraphViewModel

@Composable
fun ResilienceScoreScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onImproveResilience: () -> Unit
) {
  val simulatedScore by viewModel.scoreSimulated.collectAsState()
  val animatedScore by animateIntAsState(
    targetValue = simulatedScore,
    animationSpec = tween(600),
    label = "score"
  )

  val scoreColor = when {
    animatedScore >= 75 -> HealthyGreen
    animatedScore >= 65 -> WarningAmber
    else -> DisruptionRed
  }

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
        text = "Resilience Health",
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Large Circular Score Gauge Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(NavyCard)
        .border(1.dp, NavyBorder, RoundedCornerShape(20.dp))
        .padding(24.dp),
      contentAlignment = Alignment.Center
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.size(130.dp)
        ) {
          CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.size(130.dp),
            color = NavyCardElevated,
            strokeWidth = 12.dp
          )
          CircularProgressIndicator(
            progress = { animatedScore / 100f },
            modifier = Modifier.size(130.dp),
            color = scoreColor,
            strokeWidth = 12.dp,
            strokeCap = StrokeCap.Round
          )
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "$animatedScore",
              color = TextPrimary,
              fontSize = 38.sp,
              fontWeight = FontWeight.Black
            )
            Text(
              text = "/ 100",
              color = TextMuted,
              fontSize = 13.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
        StatusChip(
          text = if (animatedScore >= 75) "SYSTEM HEALTHY" else if (animatedScore >= 65) "MODERATE RISK" else "CRITICAL DEFICIT",
          color = scoreColor
        )
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Disruption Simulation buttons: 82 -> 74 -> 61
    Text(
      text = "SIMULATE SHOCK SCENARIOS",
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
      ScenarioScoreChip("Baseline (82)", 82, simulatedScore == 82, HealthyGreen) { viewModel.setSimulatedResilienceScore(82) }
      ScenarioScoreChip("Day 3 Delay (74)", 74, simulatedScore == 74, WarningAmber) { viewModel.setSimulatedResilienceScore(74) }
      ScenarioScoreChip("Day 6 Crisis (61)", 61, simulatedScore == 61, DisruptionRed) { viewModel.setSimulatedResilienceScore(61) }
    }

    Spacer(modifier = Modifier.height(22.dp))

    // Dimension Breakdown Bars
    Text(
      text = "NETWORK RESILIENCE VECTORS",
      color = TextSecondary,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(12.dp))

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(NavyCard)
        .border(1.dp, NavyBorder, RoundedCornerShape(16.dp))
        .padding(18.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        DimensionBar(
          name = "Supplier Diversity",
          pct = if (simulatedScore == 61) 50 else 80,
          barColor = CyanAccent
        )
        DimensionBar(
          name = "Inventory Coverage",
          pct = if (simulatedScore == 61) 35 else if (simulatedScore == 74) 55 else 70,
          barColor = if (simulatedScore < 70) DisruptionRed else WarningAmber
        )
        DimensionBar(
          name = "Logistics Redundancy",
          pct = 80,
          barColor = CyanAccent
        )
        DimensionBar(
          name = "Recovery Readiness",
          pct = if (simulatedScore == 61) 45 else 60,
          barColor = WarningAmber
        )
        DimensionBar(
          name = "Risk Exposure",
          pct = if (simulatedScore == 61) 90 else 80,
          barColor = if (simulatedScore == 61) DisruptionRed else HealthyGreen
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Explanatory note
    Text(
      text = "“Your resilience score changes as supplier risk, inventory coverage and recovery readiness change.”",
      color = TextSecondary,
      fontSize = 12.sp,
      fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
      lineHeight = 16.sp
    )

    Spacer(modifier = Modifier.height(24.dp))

    PrimaryActionButton(
      text = "Improve Resilience",
      onClick = onImproveResilience,
      icon = Icons.Default.ArrowForward,
      color = SapBlue,
      modifier = Modifier.testTag("improve_resilience_button")
    )

    Spacer(modifier = Modifier.height(30.dp))
  }
}

@Composable
private fun ScenarioScoreChip(
  label: String,
  score: Int,
  isSelected: Boolean,
  color: Color,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(12.dp))
      .background(if (isSelected) color.copy(alpha = 0.2f) else NavyCard)
      .border(1.dp, if (isSelected) color else NavyBorder, RoundedCornerShape(12.dp))
      .clickable { onClick() }
      .padding(horizontal = 10.dp, vertical = 8.dp)
  ) {
    Text(
      text = label,
      color = if (isSelected) color else TextSecondary,
      fontSize = 11.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
    )
  }
}

@Composable
private fun DimensionBar(
  name: String,
  pct: Int,
  barColor: Color
) {
  Column {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(name, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
      Text("$pct%", color = barColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(6.dp))
    LinearProgressIndicator(
      progress = { pct / 100f },
      modifier = Modifier
        .fillMaxWidth()
        .height(8.dp)
        .clip(RoundedCornerShape(4.dp)),
      color = barColor,
      trackColor = NavyCardElevated,
      strokeCap = StrokeCap.Round
    )
  }
}
