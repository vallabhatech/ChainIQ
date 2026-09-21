package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber
import com.example.viewmodel.ResiliGraphViewModel

@Composable
fun IncidentAnalysisScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onSeeImpactRadius: () -> Unit
) {
  val incident by viewModel.incident.collectAsState()
  val analysisStep by viewModel.analysisStep.collectAsState()

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
        text = "Incident Analysis",
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "Disruption Detected",
      color = TextPrimary,
      fontSize = 24.sp,
      fontWeight = FontWeight.ExtraBold
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Large Red/Orange Alert Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(
          Brush.verticalGradient(
            colors = listOf(
              DisruptionRed.copy(alpha = 0.2f),
              NavyCard
            )
          )
        )
        .border(1.5.dp, DisruptionRed.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
        .padding(20.dp)
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = incident.supplierName,
            color = TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
          )
          StatusChip(text = "SEVERITY: ${incident.severity}", color = DisruptionRed)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Delivery disruption",
          color = WarningAmber,
          fontSize = 15.sp,
          fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text("Duration", color = TextMuted, fontSize = 11.sp)
            Text("${incident.durationDays} days", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
          }
          Column {
            Text("Material", color = TextMuted, fontSize = 11.sp)
            Text(incident.componentName, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
          }
          Column {
            Text("Stockout Window", color = TextMuted, fontSize = 11.sp)
            Text("in ${incident.daysToShortage} days", color = DisruptionRed, fontSize = 14.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(22.dp))

    // WHAT RESILIGRAPH UNDERSTOOD
    Text(
      text = "WHAT RESILIGRAPH UNDERSTOOD",
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
      UnderstoodChip(label = "Supplier", value = incident.supplierName, modifier = Modifier.weight(1f))
      UnderstoodChip(label = "Duration", value = "${incident.durationDays} Days", modifier = Modifier.weight(1f))
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      UnderstoodChip(label = "Material", value = "Component X", modifier = Modifier.weight(1f))
      UnderstoodChip(label = "Impact", value = "Potential shortage", modifier = Modifier.weight(1f))
    }

    Spacer(modifier = Modifier.height(24.dp))

    // AI IS ANALYZING
    Text(
      text = "AI IS ANALYZING",
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
        AnalysisStepRow(
          title = "Supplier identified",
          subtitle = "Supplier A matched to vendor record #VN-4091",
          isDone = analysisStep >= 1
        )
        AnalysisStepRow(
          title = "Material identified",
          subtitle = "Critical Component X BOM dependencies indexed",
          isDone = analysisStep >= 2
        )
        AnalysisStepRow(
          title = "Network dependencies found",
          subtitle = "Traversed 2 assembly plants and 1 central warehouse",
          isDone = analysisStep >= 3
        )
        AnalysisStepRow(
          title = if (analysisStep >= 4) "Impact calculated" else "Calculating impact",
          subtitle = if (analysisStep >= 4) "Shortage forecast: Plant 02 stops in 6 days" else "Simulating inventory run-out curves...",
          isDone = analysisStep >= 4,
          isInProgress = analysisStep < 4
        )
      }
    }

    Spacer(modifier = Modifier.height(28.dp))

    PrimaryActionButton(
      text = "See Impact Radius",
      onClick = onSeeImpactRadius,
      icon = Icons.Default.ArrowForward,
      color = DisruptionRed,
      modifier = Modifier.testTag("see_impact_radius_button")
    )

    Spacer(modifier = Modifier.height(30.dp))
  }
}

@Composable
private fun UnderstoodChip(label: String, value: String, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(NavyCard)
      .border(1.dp, NavyBorder, RoundedCornerShape(12.dp))
      .padding(12.dp)
  ) {
    Column {
      Text(label, color = TextMuted, fontSize = 11.sp)
      Spacer(modifier = Modifier.height(2.dp))
      Text(value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
private fun AnalysisStepRow(
  title: String,
  subtitle: String,
  isDone: Boolean,
  isInProgress: Boolean = false
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    if (isDone) {
      Box(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape)
          .background(HealthyGreen.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(Icons.Default.Check, contentDescription = null, tint = HealthyGreen, modifier = Modifier.size(16.dp))
      }
    } else if (isInProgress) {
      CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = CyanAccent,
        strokeWidth = 2.5.dp
      )
    } else {
      Box(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape)
          .background(NavyCardElevated),
        contentAlignment = Alignment.Center
      ) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(TextMuted))
      }
    }

    Spacer(modifier = Modifier.width(14.dp))
    Column {
      Text(
        text = title,
        color = if (isDone) TextPrimary else if (isInProgress) CyanAccent else TextMuted,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
      )
      Text(
        text = subtitle,
        color = TextMuted,
        fontSize = 11.sp
      )
    }
  }
}
