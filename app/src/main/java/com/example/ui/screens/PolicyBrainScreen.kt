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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.data.BusinessRule
import com.example.ui.theme.CyanAccent
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
fun PolicyBrainScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit
) {
  val rules by viewModel.businessRules.collectAsState()
  var showAddDialog by remember { mutableStateOf(false) }
  var newRuleTitle by remember { mutableStateOf("") }
  var newRuleCategory by remember { mutableStateOf("Risk") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyBackground)
      .padding(horizontal = 20.dp)
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
        text = "Policy Brain",
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "Business Rules",
      color = TextPrimary,
      fontSize = 24.sp,
      fontWeight = FontWeight.ExtraBold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Tell ResiliGraph how your company wants decisions made.",
      color = TextSecondary,
      fontSize = 13.sp
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Add Rule Action Button
    OutlinedButton(
      onClick = { showAddDialog = true },
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .testTag("add_business_rule_button"),
      shape = RoundedCornerShape(14.dp),
      colors = ButtonDefaults.outlinedButtonColors(contentColor = CyanAccent),
      border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.6f))
    ) {
      Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
      Spacer(modifier = Modifier.width(8.dp))
      Text("+ Add Business Rule", fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Rules List
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier.weight(1f)
    ) {
      items(rules) { rule ->
        RuleCardItem(
          rule = rule,
          onToggle = { _ -> viewModel.toggleBusinessRule(rule.id) }
        )
      }
      item {
        Spacer(modifier = Modifier.height(30.dp))
      }
    }
  }

  // Add Rule Dialog
  if (showAddDialog) {
    AlertDialog(
      onDismissRequest = { showAddDialog = false },
      containerColor = NavyCard,
      shape = RoundedCornerShape(20.dp),
      title = {
        Text("Describe your rule", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            "State corporate constraint or preference in plain language.",
            color = TextSecondary,
            fontSize = 12.sp
          )
          OutlinedTextField(
            value = newRuleTitle,
            onValueChange = { newRuleTitle = it },
            placeholder = { Text("Example: Avoid suppliers with high geopolitical risk.", color = TextMuted, fontSize = 13.sp) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = NavyBackground,
              unfocusedContainerColor = NavyBackground,
              focusedBorderColor = CyanAccent,
              unfocusedBorderColor = NavyBorder,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            ),
            maxLines = 3
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (newRuleTitle.isNotBlank()) {
              viewModel.addBusinessRule(newRuleTitle, newRuleCategory)
              newRuleTitle = ""
              showAddDialog = false
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
          shape = RoundedCornerShape(10.dp)
        ) {
          Text("Save Rule", color = NavyBackground, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showAddDialog = false }) {
          Text("Cancel", color = TextSecondary)
        }
      }
    )
  }
}

@Composable
private fun RuleCardItem(
  rule: BusinessRule,
  onToggle: (Boolean) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(NavyCard)
      .border(1.dp, if (rule.isEnabled) CyanAccent.copy(alpha = 0.4f) else NavyBorder, RoundedCornerShape(16.dp))
      .padding(16.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(SapBlue.copy(alpha = 0.2f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(rule.category.uppercase(), color = CyanAccent, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "“${rule.ruleText}”",
          color = if (rule.isEnabled) TextPrimary else TextMuted,
          fontSize = 14.sp,
          fontWeight = FontWeight.SemiBold,
          lineHeight = 18.sp
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Switch(
        checked = rule.isEnabled,
        onCheckedChange = onToggle,
        colors = SwitchDefaults.colors(
          checkedThumbColor = Color.White,
          checkedTrackColor = CyanAccent,
          uncheckedThumbColor = TextMuted,
          uncheckedTrackColor = NavyCardElevated
        )
      )
    }
  }
}
