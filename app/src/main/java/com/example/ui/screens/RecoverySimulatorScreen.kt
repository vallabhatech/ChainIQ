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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PrimaryActionButton
import com.example.ui.components.RecoveryPlanCard
import com.example.ui.components.StatusChip
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.NavyBackground
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCard
import com.example.ui.theme.SapBlue
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber
import com.example.viewmodel.ResiliGraphViewModel

@Composable
fun RecoverySimulatorScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onCompareAll: () -> Unit,
  onExploreOption: (String) -> Unit
) {
  val options by viewModel.recoveryOptions.collectAsState()
  val selectedOptionId by viewModel.selectedOptionId.collectAsState()
  val durationDays by viewModel.durationDays.collectAsState()

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
          text = "Recovery Simulator",
          color = TextPrimary,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    item {
      Text(
        text = "What if Supplier A stays unavailable?",
        color = TextPrimary,
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Multi-criteria evaluation for alternative sourcing channels",
        color = TextSecondary,
        fontSize = 13.sp
      )
    }

    // Disruption duration banner
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(NavyCard)
          .border(1.dp, NavyBorder, RoundedCornerShape(14.dp))
          .padding(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Tune, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Disruption duration:", color = TextSecondary, fontSize = 13.sp)
          }
          StatusChip(text = "$durationDays days active", color = WarningAmber)
        }
      }
    }

    item {
      Text(
        text = "RECOVERY OPTIONS",
        color = TextSecondary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
    }

    items(options) { option ->
      RecoveryPlanCard(
        option = option,
        isSelected = option.id == selectedOptionId,
        onSelect = { viewModel.selectRecoveryOption(option.id) },
        onExplore = {
          viewModel.selectRecoveryOption(option.id)
          onExploreOption(option.id)
        },
        modifier = Modifier.testTag("plan_card_${option.id}")
      )
    }

    item {
      Spacer(modifier = Modifier.height(10.dp))
      PrimaryActionButton(
        text = "Compare All",
        onClick = onCompareAll,
        icon = Icons.Default.ArrowForward,
        color = SapBlue,
        modifier = Modifier.testTag("compare_all_button")
      )
      Spacer(modifier = Modifier.height(80.dp))
    }
  }
}
