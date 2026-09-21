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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChatMessage
import com.example.data.ScenarioDetails
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
fun ChallengeAiScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onProceedToApproval: () -> Unit
) {
  val messages by viewModel.chatMessages.collectAsState()
  var inputQuery by remember { mutableStateOf("") }

  val suggestionChips = listOf(
    "Why not Supplier C?",
    "What if disruption lasts 30 days?",
    "Why not air cargo?",
    "Detailed cost delta?"
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyBackground)
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onNavigateBack) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
          Text("Ask ResiliGraph", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
          Text("Supply Chain Decision Assistant", color = CyanAccent, fontSize = 11.sp)
        }
      }
      Button(
        onClick = onProceedToApproval,
        colors = ButtonDefaults.buttonColors(containerColor = HealthyGreen),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.height(36.dp).testTag("chat_proceed_approval")
      ) {
        Text("Approve", fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }
    }

    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(NavyBorder))

    // Messages List
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
      contentPadding = PaddingValues(vertical = 16.dp)
    ) {
      items(messages) { msg ->
        ChatBubbleItem(message = msg)
      }
    }

    // Quick suggestion pills
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
      items(suggestionChips) { chipText ->
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .border(1.dp, NavyBorder, RoundedCornerShape(16.dp))
            .clickable { viewModel.sendUserChatMessage(chipText) }
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(chipText, color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
      }
    }

    // Bottom Input Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(NavyCard)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = { viewModel.sendUserChatMessage("What if the disruption lasts 30 days?") },
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(NavyCardElevated)
      ) {
        Icon(Icons.Default.Mic, contentDescription = "Voice Prompt", tint = CyanAccent, modifier = Modifier.size(20.dp))
      }

      Spacer(modifier = Modifier.width(8.dp))

      OutlinedTextField(
        value = inputQuery,
        onValueChange = { inputQuery = it },
        placeholder = { Text("Ask another question...", color = TextMuted, fontSize = 13.sp) },
        modifier = Modifier
          .weight(1f)
          .height(50.dp)
          .testTag("chat_input_field"),
        shape = RoundedCornerShape(25.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = NavyBackground,
          unfocusedContainerColor = NavyBackground,
          focusedBorderColor = CyanAccent,
          unfocusedBorderColor = NavyBorder,
          focusedTextColor = TextPrimary,
          unfocusedTextColor = TextPrimary,
          cursorColor = CyanAccent
        ),
        singleLine = true
      )

      Spacer(modifier = Modifier.width(8.dp))

      IconButton(
        onClick = {
          if (inputQuery.isNotBlank()) {
            viewModel.sendUserChatMessage(inputQuery)
            inputQuery = ""
          }
        },
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(SapBlue)
          .testTag("chat_send_button")
      ) {
        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = TextPrimary, modifier = Modifier.size(18.dp))
      }
    }
  }
}

@Composable
private fun ChatBubbleItem(message: ChatMessage) {
  val isUser = message.sender == "user"

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
    verticalAlignment = Alignment.Top
  ) {
    if (!isUser) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(SapBlue.copy(alpha = 0.2f))
          .border(1.dp, CyanAccent.copy(alpha = 0.5f), CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
      }
      Spacer(modifier = Modifier.width(8.dp))
    }

    Column(
      modifier = Modifier
        .widthIn(max = 290.dp)
        .clip(
          RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = if (isUser) 16.dp else 4.dp,
            bottomEnd = if (isUser) 4.dp else 16.dp
          )
        )
        .background(if (isUser) SapBlue else NavyCard)
        .border(1.dp, if (isUser) SapBlue else NavyBorder, RoundedCornerShape(16.dp))
        .padding(14.dp)
    ) {
      Text(
        text = message.text,
        color = TextPrimary,
        fontSize = 13.sp,
        lineHeight = 18.sp
      )

      if (message.scenarioDetails != null) {
        Spacer(modifier = Modifier.height(10.dp))
        ScenarioCardView(details = message.scenarioDetails)
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = message.timestamp,
        color = if (isUser) Color.White.copy(alpha = 0.7f) else TextMuted,
        fontSize = 10.sp,
        modifier = Modifier.align(Alignment.End)
      )
    }

    if (isUser) {
      Spacer(modifier = Modifier.width(8.dp))
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(NavyCardElevated),
        contentAlignment = Alignment.Center
      ) {
        Icon(Icons.Default.Person, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
      }
    }
  }
}

@Composable
private fun ScenarioCardView(details: ScenarioDetails) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(NavyCardElevated)
      .border(1.dp, DisruptionRed.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
      .padding(10.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text("30-Day Scenario Model", color = DisruptionRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        StatusChip(text = "STRESS TEST", color = DisruptionRed)
      }
      Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text("Production impact:", color = TextMuted, fontSize = 11.sp)
        Text(details.productionImpact, color = DisruptionRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
      }
      Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text("Orders at risk:", color = TextMuted, fontSize = 11.sp)
        Text("${details.ordersAtRisk}", color = WarningAmber, fontSize = 11.sp, fontWeight = FontWeight.Bold)
      }
      Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text("Recovery cost:", color = TextMuted, fontSize = 11.sp)
        Text(details.recoveryCostDelta, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
      }
      Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text("Suggested strategy:", color = TextMuted, fontSize = 11.sp)
        Text(details.suggestedStrategy, color = CyanAccent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
      }
    }
  }
}
