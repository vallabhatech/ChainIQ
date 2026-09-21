package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MockData
import com.example.data.TimelineStep
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
fun ImpactTimeMachineScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onSimulateRecovery: () -> Unit
) {
  val durationDays by viewModel.durationDays.collectAsState()
  val timelineSteps = MockData.getTimelineSteps(durationDays)

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
        text = "Time Machine",
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "What Happens Next?",
      color = TextPrimary,
      fontSize = 24.sp,
      fontWeight = FontWeight.ExtraBold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "See how the disruption could develop over time.",
      color = TextSecondary,
      fontSize = 13.sp
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Horizontal Scrollable Timeline
    Text(
      text = "PROJECTED HORIZON ($durationDays DAYS)",
      color = CyanAccent,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )

    Spacer(modifier = Modifier.height(10.dp))

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(14.dp),
      contentPadding = PaddingValues(vertical = 4.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      itemsIndexed(timelineSteps) { index, step ->
        TimelineItemCard(step = step, index = index, totalCount = timelineSteps.size)
      }
    }

    Spacer(modifier = Modifier.height(26.dp))

    // Disruption Duration Slider Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(NavyCard)
        .border(1.dp, NavyBorder, RoundedCornerShape(18.dp))
        .padding(18.dp)
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Disruption Duration",
            color = TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = "$durationDays days",
            color = if (durationDays >= 25) DisruptionRed else if (durationDays >= 15) WarningAmber else HealthyGreen,
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Duration Slider (5 .. 30)
        Slider(
          value = durationDays.toFloat(),
          onValueChange = { viewModel.setDurationDays(it.toInt()) },
          valueRange = 5f..30f,
          steps = 4, // 5, 10, 15, 20, 25, 30
          colors = SliderDefaults.colors(
            thumbColor = CyanAccent,
            activeTrackColor = SapBlue,
            inactiveTrackColor = NavyCardElevated
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("disruption_duration_slider")
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("5 days", color = TextMuted, fontSize = 11.sp, modifier = Modifier.clickable { viewModel.setDurationDays(5) })
          Text("15 days (Scenario)", color = WarningAmber, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { viewModel.setDurationDays(15) })
          Text("30 days", color = TextMuted, fontSize = 11.sp, modifier = Modifier.clickable { viewModel.setDurationDays(30) })
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Summary of slider impact
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(NavyCardElevated)
        .border(1.dp, NavyBorder, RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(if (durationDays > 10) DisruptionRed.copy(alpha = 0.2f) else HealthyGreen.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = if (durationDays > 10) DisruptionRed else HealthyGreen,
            modifier = Modifier.size(16.dp)
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = if (durationDays <= 7) "Short Disruption: Buffers hold with minor delays"
            else if (durationDays <= 18) "15-Day Disruption: Stockout on Day 6; Line stoppage on Day 8"
            else "30-Day Disruption: Catastrophic 31-order breach; Dual-sourcing mandatory",
            color = TextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(28.dp))

    PrimaryActionButton(
      text = "Simulate Recovery",
      onClick = onSimulateRecovery,
      icon = Icons.Default.ArrowForward,
      color = SapBlue,
      modifier = Modifier.testTag("simulate_recovery_button")
    )

    Spacer(modifier = Modifier.height(30.dp))
  }
}

@Composable
private fun TimelineItemCard(step: TimelineStep, index: Int, totalCount: Int) {
  val cardBorder = when (step.severity) {
    "CRITICAL" -> DisruptionRed
    "WARNING" -> WarningAmber
    else -> SapBlueLight
  }

  Box(
    modifier = Modifier
      .width(160.dp)
      .height(190.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(NavyCard)
      .border(1.dp, cardBorder.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = step.dayLabel,
            color = cardBorder,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold
          )
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(cardBorder)
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = step.title,
          color = TextPrimary,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          lineHeight = 18.sp
        )
      }

      Column {
        Text(
          text = step.description,
          color = TextSecondary,
          fontSize = 11.sp,
          lineHeight = 14.sp
        )
        if (step.badge.isNotEmpty()) {
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = step.badge,
            color = cardBorder,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
