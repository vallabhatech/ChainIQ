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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AlertCard
import com.example.ui.components.ResilienceScoreWidget
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
import com.example.ui.theme.SapBlueLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber
import com.example.viewmodel.ResiliGraphViewModel

@Composable
fun HomeScreen(
  viewModel: ResiliGraphViewModel,
  onNavigate: (String) -> Unit
) {
  val incident by viewModel.incident.collectAsState()
  val resilienceScore by viewModel.resilienceScore.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyBackground)
      .padding(horizontal = 20.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {
    // Top Bar / Title
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Good morning",
            color = TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = "Your supply network is being monitored.",
            color = TextSecondary,
            fontSize = 13.sp
          )
        }
        Box(
          modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(SapBlue.copy(alpha = 0.2f))
            .border(1.dp, SapBlueLight.copy(alpha = 0.4f), CircleShape)
            .clickable { onNavigate(Routes.SETTINGS) },
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Hub,
            contentDescription = "Network Hub",
            tint = CyanAccent,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }

    // Large Resilience Score Card
    item {
      ResilienceScoreWidget(
        score = resilienceScore,
        statusLabel = if (resilienceScore >= 75) "Stable" else "At Risk",
        supportingText = "1 emerging risk detected",
        onClick = { onNavigate(Routes.RESILIENCE_SCORE) },
        modifier = Modifier.testTag("home_resilience_card")
      )
    }

    // ACTIVE ALERT
    item {
      AlertCard(
        title = "${incident.supplierName} Disruption",
        description = "${incident.componentName} delayed by ${incident.durationDays} days",
        shortageDays = incident.daysToShortage,
        onActionClick = { onNavigate(Routes.INCIDENT_ANALYSIS) },
        modifier = Modifier.testTag("home_active_alert_card")
      )
    }

    // QUICK ACTIONS
    item {
      Column {
        Text(
          text = "QUICK ACTIONS",
          color = TextSecondary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          QuickActionCard(
            title = "Report Disruption",
            icon = Icons.Default.Mic,
            accentColor = DisruptionRed,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(Routes.REPORT_DISRUPTION) }
          )
          QuickActionCard(
            title = "Investigate Risk",
            icon = Icons.Default.Search,
            accentColor = WarningAmber,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(Routes.HIDDEN_DEPENDENCY) }
          )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          QuickActionCard(
            title = "Simulate Recovery",
            icon = Icons.Default.AltRoute,
            accentColor = SapBlueLight,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(Routes.RECOVERY_SIMULATOR) }
          )
          QuickActionCard(
            title = "Explore Network",
            icon = Icons.Default.Hub,
            accentColor = CyanAccent,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(Routes.SUPPLY_GRAPH) }
          )
        }
      }
    }

    // NETWORK HEALTH
    item {
      Column {
        Text(
          text = "NETWORK HEALTH",
          color = TextSecondary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          SmallMetricCard("12", "Suppliers", Icons.Default.Business, Modifier.weight(1f))
          SmallMetricCard("4", "Plants", Icons.Default.Factory, Modifier.weight(1f))
          SmallMetricCard("28", "Active Routes", Icons.Default.Route, Modifier.weight(1f))
          SmallMetricCard("96", "Orders", Icons.Default.ShoppingCart, Modifier.weight(1f))
        }
      }
    }

    // RECENT ACTIVITY
    item {
      Column {
        Text(
          text = "RECENT ACTIVITY",
          color = TextSecondary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        ActivityItem(
          title = "Supplier B risk increased",
          time = "10 mins ago",
          icon = Icons.Default.Warning,
          iconTint = WarningAmber
        )
        Spacer(modifier = Modifier.height(8.dp))
        ActivityItem(
          title = "Plant 02 inventory updated",
          time = "35 mins ago",
          icon = Icons.Default.Factory,
          iconTint = SapBlueLight
        )
        Spacer(modifier = Modifier.height(8.dp))
        ActivityItem(
          title = "Recovery simulation completed",
          time = "1 hour ago",
          icon = Icons.Default.CheckCircle,
          iconTint = HealthyGreen
        )
      }
    }

    item {
      Spacer(modifier = Modifier.height(80.dp))
    }
  }
}

@Composable
private fun QuickActionCard(
  title: String,
  icon: ImageVector,
  accentColor: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Box(
    modifier = modifier
      .height(96.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(NavyCard)
      .border(1.dp, NavyBorder, RoundedCornerShape(16.dp))
      .clickable { onClick() }
      .padding(14.dp)
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(accentColor.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = accentColor,
          modifier = Modifier.size(18.dp)
        )
      }
      Text(
        text = title,
        color = TextPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
      )
    }
  }
}

@Composable
private fun SmallMetricCard(
  value: String,
  label: String,
  icon: ImageVector,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(14.dp))
      .background(NavyCard)
      .border(1.dp, NavyBorder, RoundedCornerShape(14.dp))
      .padding(vertical = 12.dp, horizontal = 8.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Icon(icon, contentDescription = label, tint = CyanAccent, modifier = Modifier.size(16.dp))
      Spacer(modifier = Modifier.height(4.dp))
      Text(value, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(2.dp))
      Text(label, color = TextMuted, fontSize = 10.sp)
    }
  }
}

@Composable
private fun ActivityItem(
  title: String,
  time: String,
  icon: ImageVector,
  iconTint: Color
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(NavyCard.copy(alpha = 0.6f))
      .border(1.dp, NavyBorder.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(30.dp)
        .clip(CircleShape)
        .background(iconTint.copy(alpha = 0.15f)),
      contentAlignment = Alignment.Center
    ) {
      Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(16.dp))
    }
    Spacer(modifier = Modifier.width(12.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
      Text(time, color = TextMuted, fontSize = 11.sp)
    }
  }
}
