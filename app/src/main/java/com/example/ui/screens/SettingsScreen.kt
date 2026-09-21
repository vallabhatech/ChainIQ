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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onOpenPolicyBrain: () -> Unit
) {
  val selectedIndustry by viewModel.selectedIndustry.collectAsState()
  var notifShortage by remember { mutableStateOf(true) }
  var notifGeo by remember { mutableStateOf(true) }
  var autoSimulate by remember { mutableStateOf(true) }

  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  val industries = listOf("Automotive", "Manufacturing", "Pharma", "Electronics", "Food", "Retail")

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyBackground)
      .padding(horizontal = 20.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(16.dp))
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
          text = "Settings",
          color = TextPrimary,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    // DEMO SCENARIO SECTION (HIGH PRIORITY)
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp))
          .background(NavyCardElevated)
          .border(1.5.dp, DisruptionRed.copy(alpha = 0.6f), RoundedCornerShape(18.dp))
          .padding(18.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "DEMO CRISIS SCENARIO",
              color = DisruptionRed,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
            Icon(Icons.Default.Refresh, contentDescription = null, tint = DisruptionRed, modifier = Modifier.size(16.dp))
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Reset all mock state to the 15-day Supplier A stockout incident.",
            color = TextSecondary,
            fontSize = 12.sp
          )
          Spacer(modifier = Modifier.height(14.dp))
          Button(
            onClick = {
              viewModel.loadDemoScenario()
              scope.launch {
                snackbarHostState.showSnackbar("Supplier A Crisis Scenario loaded!")
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = DisruptionRed),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(46.dp)
              .testTag("load_demo_crisis_button")
          ) {
            Text("Load Supplier A Crisis", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }
        }
      }
    }

    // COMPANY PROFILE
    item {
      SettingsSectionHeader("COMPANY PROFILE")
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(NavyCard)
          .border(1.dp, NavyBorder, RoundedCornerShape(16.dp))
          .padding(16.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(SapBlue.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.Business, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(22.dp))
          }
          Spacer(modifier = Modifier.width(14.dp))
          Column {
            Text("Acme Industrial AG", color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text("Leipzig Manufacturing & Assembly Hub", color = TextSecondary, fontSize = 12.sp)
            Text("SAP ERP Instance: S4H-PRD-01", color = TextMuted, fontSize = 11.sp)
          }
        }
      }
    }

    // INDUSTRY SELECTOR
    item {
      SettingsSectionHeader("INDUSTRY DOMAIN")
      LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(industries) { ind ->
          val isSelected = ind == selectedIndustry
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(if (isSelected) SapBlue.copy(alpha = 0.3f) else NavyCard)
              .border(1.dp, if (isSelected) CyanAccent else NavyBorder, RoundedCornerShape(12.dp))
              .clickable { viewModel.setIndustry(ind) }
              .padding(horizontal = 14.dp, vertical = 8.dp)
          ) {
            Text(
              text = ind,
              color = if (isSelected) CyanAccent else TextSecondary,
              fontSize = 12.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
          }
        }
      }
    }

    // POLICY BRAIN LINK
    item {
      SettingsSectionHeader("GOVERNANCE & RULES")
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(NavyCard)
          .border(1.dp, NavyBorder, RoundedCornerShape(16.dp))
          .clickable { onOpenPolicyBrain() }
          .padding(16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Gavel, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text("Policy Brain Rules", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
              Text("Configure company decision thresholds", color = TextSecondary, fontSize = 11.sp)
            }
          }
          Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
        }
      }
    }

    // NOTIFICATION PREFERENCES
    item {
      SettingsSectionHeader("NOTIFICATION PREFERENCES")
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(NavyCard)
          .border(1.dp, NavyBorder, RoundedCornerShape(16.dp))
          .padding(16.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
          SettingToggleRow("Critical Stockout Warnings", "Push alert when buffer < 7 days", notifShortage) { notifShortage = it }
          SettingToggleRow("Real-Time Route Telemetry", "Carrier delays & port congestions", notifGeo) { notifGeo = it }
          SettingToggleRow("Autonomous Crisis Simulation", "Auto-run multi-criteria models", autoSimulate) { autoSimulate = it }
        }
      }
    }

    // DATA & PRIVACY
    item {
      SettingsSectionHeader("DATA & PRIVACY")
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(NavyCard)
          .border(1.dp, NavyBorder, RoundedCornerShape(16.dp))
          .padding(16.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Security, contentDescription = null, tint = HealthyGreen, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text("Mock Enterprise Sandbox", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text("Simulated local prototype environment. No external network transmission.", color = TextMuted, fontSize = 11.sp)
          }
        }
      }
    }

    item {
      SnackbarHost(hostState = snackbarHostState)
      Spacer(modifier = Modifier.height(60.dp))
    }
  }
}

@Composable
private fun SettingsSectionHeader(title: String) {
  Text(
    text = title,
    color = TextSecondary,
    fontSize = 11.sp,
    fontWeight = FontWeight.Bold,
    letterSpacing = 1.sp
  )
}

@Composable
private fun SettingToggleRow(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
      Text(subtitle, color = TextMuted, fontSize = 11.sp)
    }
    Switch(
      checked = checked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = Color.White,
        checkedTrackColor = CyanAccent,
        uncheckedThumbColor = TextMuted,
        uncheckedTrackColor = NavyCardElevated
      )
    )
  }
}
