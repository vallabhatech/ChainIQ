package com.example.ui.screens

import androidx.compose.animation.core.Animatable
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.example.viewmodel.ResiliGraphViewModel

@Composable
fun RecoverySuccessScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateHome: () -> Unit
) {
  val checkScale = remember { Animatable(0.4f) }

  LaunchedEffect(Unit) {
    checkScale.animateTo(1f, animationSpec = tween(500))
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyBackground)
      .padding(horizontal = 20.dp)
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(30.dp))

    // Large Success Check Animation
    Box(
      modifier = Modifier
        .scale(checkScale.value)
        .size(88.dp)
        .clip(CircleShape)
        .background(HealthyGreen)
        .border(4.dp, HealthyGreen.copy(alpha = 0.4f), CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.Check,
        contentDescription = "Success",
        tint = Color.Black,
        modifier = Modifier.size(52.dp)
      )
    }

    Spacer(modifier = Modifier.height(18.dp))

    Text(
      text = "Recovery Plan Approved",
      color = TextPrimary,
      fontSize = 24.sp,
      fontWeight = FontWeight.Black
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = "Purchase order dispatched to Supplier B via SAP integration.",
      color = TextSecondary,
      fontSize = 13.sp
    )

    Spacer(modifier = Modifier.height(24.dp))

    // Flow visual: Supplier B -> Plant 02 -> Warehouse B
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(NavyCard)
        .border(1.dp, HealthyGreen.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
        .padding(18.dp)
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "RECOVERY INITIATED",
            color = HealthyGreen,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          StatusChip(text = "ACTIVE DISPATCH", color = HealthyGreen)
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Visual Pipeline
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          PipeStep("Supplier B", "Nuremberg", HealthyGreen)
          Icon(Icons.Default.ArrowForward, contentDescription = null, tint = HealthyGreen, modifier = Modifier.size(16.dp))
          PipeStep("Plant 02", "Leipzig", HealthyGreen)
          Icon(Icons.Default.ArrowForward, contentDescription = null, tint = HealthyGreen, modifier = Modifier.size(16.dp))
          PipeStep("Warehouse B", "Munich", HealthyGreen)
        }

        Spacer(modifier = Modifier.height(14.dp))
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(NavyBorder))
        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text("Shortage Risk", color = TextMuted, fontSize = 11.sp)
            Text("LOW (Mitigated)", color = HealthyGreen, fontSize = 13.sp, fontWeight = FontWeight.Bold)
          }
          Column {
            Text("Recovery Time", color = TextMuted, fontSize = 11.sp)
            Text("5 days", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
          }
          Column {
            Text("Line Stoppage", color = TextMuted, fontSize = 11.sp)
            Text("0 hours averted", color = CyanAccent, fontSize = 13.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Resilience Score Improvement Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(NavyCardElevated)
        .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
        .padding(18.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "SUPPLY RESILIENCE RECOVERY",
            color = CyanAccent,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "61",
              color = DisruptionRed,
              fontSize = 26.sp,
              fontWeight = FontWeight.Bold
            )
            Text("  →  ", color = TextMuted, fontSize = 20.sp)
            Text(
              text = "78",
              color = HealthyGreen,
              fontSize = 28.sp,
              fontWeight = FontWeight.Black
            )
          }
          Text(
            text = "Your network is recovering.",
            color = HealthyGreen,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(HealthyGreen.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.TrendingUp,
            contentDescription = null,
            tint = HealthyGreen,
            modifier = Modifier.size(24.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(28.dp))

    PrimaryActionButton(
      text = "Back to Home",
      onClick = onNavigateHome,
      icon = Icons.Default.Home,
      color = SapBlue,
      modifier = Modifier.testTag("back_to_home_button")
    )

    Spacer(modifier = Modifier.height(30.dp))
  }
}

@Composable
private fun PipeStep(title: String, location: String, color: Color) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Box(
      modifier = Modifier
        .size(12.dp)
        .clip(CircleShape)
        .background(color)
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(title, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    Text(location, color = TextMuted, fontSize = 10.sp)
  }
}
