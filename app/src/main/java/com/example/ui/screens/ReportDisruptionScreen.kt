package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PrimaryActionButton
import com.example.ui.navigation.Routes
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DisruptionRed
import com.example.ui.theme.DisruptionRedGlow
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
fun ReportDisruptionScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onAnalyze: () -> Unit
) {
  val text by viewModel.inputText.collectAsState()
  val isRecording by viewModel.isRecordingVoice.collectAsState()
  val uploadedDoc by viewModel.uploadedDocument.collectAsState()

  val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
  val micScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.25f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "micScale"
  )

  val wavePhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(400, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "wave"
  )

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
        text = "Report Disruption",
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(18.dp))

    Text(
      text = "What happened?",
      color = TextPrimary,
      fontSize = 26.sp,
      fontWeight = FontWeight.ExtraBold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Tell ResiliGraph in your own words.",
      color = TextSecondary,
      fontSize = 14.sp
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Large Input Box
    OutlinedTextField(
      value = text,
      onValueChange = { viewModel.updateInputText(it) },
      placeholder = {
        Text(
          "Example: Supplier A stopped shipment for 15 days.",
          color = TextMuted,
          fontSize = 14.sp
        )
      },
      modifier = Modifier
        .fillMaxWidth()
        .height(130.dp)
        .testTag("disruption_text_input"),
      shape = RoundedCornerShape(16.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = NavyCard,
        unfocusedContainerColor = NavyCard,
        focusedBorderColor = CyanAccent,
        unfocusedBorderColor = NavyBorder,
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        cursorColor = CyanAccent
      ),
      maxLines = 4
    )

    Spacer(modifier = Modifier.height(14.dp))

    // Document Upload Simulation
    if (uploadedDoc != null) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(NavyCardElevated)
          .border(1.dp, CyanAccent.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
          .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Description, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(uploadedDoc ?: "", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
        IconButton(onClick = { viewModel.clearUploadedDocument() }, modifier = Modifier.size(24.dp)) {
          Icon(Icons.Default.Close, contentDescription = "Remove", tint = TextMuted, modifier = Modifier.size(16.dp))
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    } else {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        OutlinedButton(
          onClick = { viewModel.simulateDocumentUpload() },
          shape = RoundedCornerShape(10.dp),
          colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
            contentColor = TextSecondary
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, NavyBorder),
          modifier = Modifier.testTag("upload_document_button")
        ) {
          Icon(Icons.Default.AttachFile, contentDescription = "Upload Document", modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Upload document", fontSize = 12.sp)
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Voice Recording Area
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(NavyCard)
        .border(1.dp, if (isRecording) DisruptionRed else NavyBorder, RoundedCornerShape(20.dp))
        .padding(vertical = 24.dp),
      contentAlignment = Alignment.Center
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
          text = "OR USE VOICE",
          color = TextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.5.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Big Mic Button with Pulse
        Box(
          modifier = Modifier
            .size(80.dp)
            .scale(if (isRecording) micScale else 1f)
            .clip(CircleShape)
            .background(
              if (isRecording) DisruptionRed else SapBlue
            )
            .clickable { viewModel.startSimulatedVoiceRecording() }
            .testTag("voice_record_button"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = "Tap to speak",
            tint = TextPrimary,
            modifier = Modifier.size(36.dp)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))
        Text(
          text = if (isRecording) "Listening..." else "Tap to speak",
          color = if (isRecording) DisruptionRed else TextPrimary,
          fontSize = 15.sp,
          fontWeight = FontWeight.SemiBold
        )

        if (isRecording) {
          Spacer(modifier = Modifier.height(12.dp))
          // Waveform bars
          Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            val heights = listOf(14.dp, 28.dp, 40.dp, 22.dp, 36.dp, 18.dp, 30.dp, 12.dp)
            heights.forEachIndexed { index, baseHeight ->
              val dynH = (baseHeight.value * (0.6f + 0.4f * ((wavePhase + index * 0.15f) % 1f))).dp
              Box(
                modifier = Modifier
                  .width(4.dp)
                  .height(dynH)
                  .clip(RoundedCornerShape(2.dp))
                  .background(CyanAccent)
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.weight(1f))

    // Primary Button
    PrimaryActionButton(
      text = "Analyze Disruption",
      onClick = {
        viewModel.startAnalysisSimulation()
        onAnalyze()
      },
      icon = Icons.Default.ArrowForward,
      color = SapBlue,
      modifier = Modifier.testTag("analyze_disruption_button")
    )

    Spacer(modifier = Modifier.height(24.dp))
  }
}
