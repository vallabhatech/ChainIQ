package com.example.ui.screens

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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PrimaryActionButton
import com.example.ui.components.StatusChip
import com.example.ui.theme.CyanAccent
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
import com.example.viewmodel.ResiliGraphViewModel

@Composable
fun RecommendationScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onCompareAgain: () -> Unit,
  onSimulate: () -> Unit,
  onChallengeAi: () -> Unit,
  onProceedToApproval: () -> Unit
) {
  var showWhyRationaleDialog by remember { mutableStateOf(false) }

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
        text = "AI Recommendation",
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "ResiliGraph Recommendation",
      color = TextPrimary,
      fontSize = 24.sp,
      fontWeight = FontWeight.ExtraBold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Synthesized multi-criteria crisis resolution",
      color = TextSecondary,
      fontSize = 13.sp
    )

    Spacer(modifier = Modifier.height(18.dp))

    // Large Recommendation Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(
          Brush.verticalGradient(
            colors = listOf(
              SapBlue.copy(alpha = 0.22f),
              NavyCardElevated
            )
          )
        )
        .border(1.5.dp, CyanAccent.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
        .padding(20.dp)
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "TOP STRATEGY",
              color = CyanAccent,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }
          StatusChip(text = "Confidence: 91%", color = HealthyGreen)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "PLAN A",
          color = TextMuted,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Text(
          text = "Local Supplier + Truck",
          color = TextPrimary,
          fontSize = 22.sp,
          fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "WHY?",
          color = TextSecondary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        val whyPoints = listOf(
          "Prevents predicted shortage in 6 days",
          "Low disruption risk with certified vendor",
          "Fast enough for current inventory (5-day turnaround)",
          "Moderate additional cost (+$8,500 vs $340k line stop)",
          "Lower carbon impact (-38% vs Air)"
        )

        whyPoints.forEach { point ->
          Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.Top
          ) {
            Box(
              modifier = Modifier
                .padding(top = 2.dp)
                .size(18.dp)
                .clip(CircleShape)
                .background(HealthyGreen.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Check, contentDescription = null, tint = HealthyGreen, modifier = Modifier.size(12.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = point,
              color = TextPrimary,
              fontSize = 13.sp,
              lineHeight = 18.sp
            )
          }
        }
      }
    }

    if (showWhyRationaleDialog) {
      Spacer(modifier = Modifier.height(14.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(NavyCard)
          .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
          .padding(14.dp)
      ) {
        Column {
          Text(
            text = "Mathematical Rationale (Hidden)",
            color = CyanAccent,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "ResiliGraph mapped Euclidean proximity to Leipzig assembly lines, weight-ranking buffer run-out against supply-route latency.",
            color = TextSecondary,
            fontSize = 12.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Interactive action buttons grid
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      SmallActionOutlineButton(
        text = "Why this plan?",
        icon = Icons.Default.AutoAwesome,
        modifier = Modifier.weight(1f),
        onClick = { showWhyRationaleDialog = !showWhyRationaleDialog }
      )
      SmallActionOutlineButton(
        text = "Compare again",
        icon = Icons.Default.Compare,
        modifier = Modifier.weight(1f),
        onClick = onCompareAgain
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      SmallActionOutlineButton(
        text = "Simulate",
        icon = Icons.Default.Refresh,
        modifier = Modifier.weight(1f),
        onClick = onSimulate
      )
      SmallActionOutlineButton(
        text = "Challenge AI",
        icon = Icons.Default.QuestionAnswer,
        modifier = Modifier.weight(1f),
        onClick = onChallengeAi
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    PrimaryActionButton(
      text = "Proceed to Approval",
      onClick = onProceedToApproval,
      icon = Icons.Default.ArrowForward,
      color = HealthyGreen,
      modifier = Modifier.testTag("proceed_to_approval_button")
    )

    Spacer(modifier = Modifier.height(30.dp))
  }
}

@Composable
private fun SmallActionOutlineButton(
  text: String,
  icon: ImageVector,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  OutlinedButton(
    onClick = onClick,
    modifier = modifier.height(46.dp),
    shape = RoundedCornerShape(12.dp),
    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
    border = androidx.compose.foundation.BorderStroke(1.dp, NavyBorder)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(icon, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Text(text, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
  }
}
