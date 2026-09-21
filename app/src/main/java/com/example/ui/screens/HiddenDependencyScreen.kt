package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
fun HiddenDependencyScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onExploreDependency: () -> Unit
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse_dep")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.15f,
    animationSpec = infiniteRepeatable(
      animation = tween(1000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseScale"
  )

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
        text = "Deep Tier Intelligence",
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "Hidden Dependency Found",
      color = TextPrimary,
      fontSize = 24.sp,
      fontWeight = FontWeight.ExtraBold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Multi-tier graph traversal discovered unmapped supply vulnerability",
      color = TextSecondary,
      fontSize = 13.sp
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Vertical Hierarchy Flow
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(NavyCard)
        .border(1.dp, NavyBorder, RoundedCornerShape(20.dp))
        .padding(20.dp),
      contentAlignment = Alignment.Center
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        TierNodeCard(
          title = "Company (OEM)",
          subtitle = "Global Assembly Lead",
          badge = "YOUR ENTITY",
          badgeColor = CyanAccent
        )

        Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = NavyBorder, modifier = Modifier.size(20.dp))

        TierNodeCard(
          title = "Supplier A",
          subtitle = "Tier-1 Assembly Partner",
          badge = "PRIMARY VENDOR",
          badgeColor = DisruptionRed
        )

        Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(20.dp))

        // Highlighted Supplier B with glowing pulse
        Box(
          modifier = Modifier
            .scale(pulseScale)
            .clip(RoundedCornerShape(16.dp))
            .background(WarningAmber.copy(alpha = 0.15f))
            .border(2.dp, WarningAmber, RoundedCornerShape(16.dp))
            .padding(16.dp)
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Warning, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("⚠ HIDDEN TIER-2 DEPENDENCY", color = WarningAmber, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Supplier B (Nuremberg)", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("Sole provider of rare magnetic alloy for Component X", color = TextSecondary, fontSize = 11.sp)
          }
        }

        Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = NavyBorder, modifier = Modifier.size(20.dp))

        TierNodeCard(
          title = "Raw Material X",
          subtitle = "Neodymium Sintered Magnet",
          badge = "CRITICAL COMMODITY",
          badgeColor = WarningAmber
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Alert Message Box
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(NavyCardElevated)
        .border(1.dp, WarningAmber.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        .padding(16.dp)
    ) {
      Row(verticalAlignment = Alignment.Top) {
        Icon(Icons.Default.Warning, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Hidden dependency detected",
              color = WarningAmber,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
            StatusChip(text = "Risk level: MEDIUM", color = WarningAmber)
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "“Your primary supplier depends on another supplier for a critical raw material.”",
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Supplier A has only 12 days buffer of Raw Material X supplied by Supplier B. Any disruption at Supplier B halts Supplier A immediately.",
            color = TextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    PrimaryActionButton(
      text = "Explore Dependency",
      onClick = onExploreDependency,
      icon = Icons.Default.ArrowForward,
      color = SapBlue,
      modifier = Modifier.testTag("explore_dependency_button")
    )

    Spacer(modifier = Modifier.height(30.dp))
  }
}

@Composable
private fun TierNodeCard(
  title: String,
  subtitle: String,
  badge: String,
  badgeColor: Color
) {
  Box(
    modifier = Modifier
      .fillMaxWidth(0.9f)
      .clip(RoundedCornerShape(14.dp))
      .background(NavyCardElevated)
      .border(1.dp, NavyBorder, RoundedCornerShape(14.dp))
      .padding(horizontal = 16.dp, vertical = 10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = TextMuted, fontSize = 11.sp)
      }
      StatusChip(text = badge, color = badgeColor)
    }
  }
}
