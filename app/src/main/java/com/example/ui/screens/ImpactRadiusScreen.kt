package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
fun ImpactRadiusScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onSeeWhatHappensNext: () -> Unit
) {
  val infiniteTransition = rememberInfiniteTransition(label = "blast_pulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.45f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "blast_scale"
  )
  val dashPhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 40f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "dash"
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
        text = "Blast Radius",
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "How Big Is the Problem?",
          color = TextPrimary,
          fontSize = 22.sp,
          fontWeight = FontWeight.ExtraBold
        )
        Text(
          text = "Propagation path across supply tiers",
          color = TextSecondary,
          fontSize = 12.sp
        )
      }
      StatusChip(text = "RADIUS: HIGH", color = DisruptionRed)
    }

    Spacer(modifier = Modifier.height(16.dp))

    // 4 Metric Cards
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      BlastMetricCard("4", "Materials", DisruptionRed, Modifier.weight(1f))
      BlastMetricCard("2", "Plants", WarningAmber, Modifier.weight(1f))
      BlastMetricCard("17", "Orders", DisruptionRed, Modifier.weight(1f))
      BlastMetricCard("6", "Customers", CyanAccent, Modifier.weight(1f))
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Visual Blast Radius Map / Chain
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(260.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(NavyCard)
        .border(1.dp, NavyBorder, RoundedCornerShape(20.dp))
        .padding(12.dp)
    ) {
      Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Flow path nodes:
        // Node 1: Supplier A (0.15w, 0.5h)
        // Node 2: Component X (0.32w, 0.28h)
        // Node 3: Plant 02 (0.50w, 0.65h)
        // Node 4: Warehouse B (0.68w, 0.35h)
        // Node 5: 17 Orders (0.85w, 0.25h)
        // Node 6: 6 Customers (0.85w, 0.75h)

        val n1 = Offset(w * 0.12f, h * 0.50f)
        val n2 = Offset(w * 0.32f, h * 0.30f)
        val n3 = Offset(w * 0.50f, h * 0.70f)
        val n4 = Offset(w * 0.68f, h * 0.40f)
        val n5 = Offset(w * 0.88f, h * 0.28f)
        val n6 = Offset(w * 0.88f, h * 0.72f)

        // Draw propagation lines with animated dashes
        val pathPairs = listOf(
          Pair(n1, n2),
          Pair(n2, n3),
          Pair(n3, n4),
          Pair(n4, n5),
          Pair(n4, n6)
        )

        pathPairs.forEach { (start, end) ->
          drawLine(
            color = DisruptionRed.copy(alpha = 0.7f),
            start = start,
            end = end,
            strokeWidth = 2.5.dp.toPx(),
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), dashPhase)
          )
        }

        // Draw Pulsing halo on Supplier A
        drawCircle(
          color = DisruptionRed.copy(alpha = 0.22f),
          radius = 28.dp.toPx() * pulseScale,
          center = n1
        )

        // Draw Nodes
        fun drawChainNode(pos: Offset, color: Color, label: String, r: Float = 14f) {
          drawCircle(color = Color.White, radius = (r + 3).dp.toPx(), center = pos, style = Stroke(width = 2.dp.toPx()))
          drawCircle(color = color, radius = r.dp.toPx(), center = pos)
        }

        drawChainNode(n1, DisruptionRed, "Supplier A", 18f)
        drawChainNode(n2, WarningAmber, "Comp X", 14f)
        drawChainNode(n3, DisruptionRed, "Plant 02", 16f)
        drawChainNode(n4, WarningAmber, "WH-B", 14f)
        drawChainNode(n5, DisruptionRed, "17 Orders", 15f)
        drawChainNode(n6, CyanAccent, "6 Clients", 15f)
      }

      // Overlaid Node Labels
      Box(modifier = Modifier.fillMaxSize()) {
        Text("🔴 Supplier A", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomStart).padding(start = 4.dp, bottom = 4.dp))
        Text("Component X", color = TextSecondary, fontSize = 10.sp, modifier = Modifier.align(Alignment.TopStart).padding(start = 75.dp, top = 22.dp))
        Text("Plant 02", color = TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp))
        Text("Warehouse B", color = TextSecondary, fontSize = 10.sp, modifier = Modifier.align(Alignment.TopCenter).padding(start = 70.dp, top = 40.dp))
        Text("17 Orders", color = DisruptionRed, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.TopEnd).padding(end = 4.dp, top = 20.dp))
        Text("6 Customers", color = CyanAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomEnd).padding(end = 4.dp, bottom = 12.dp))
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // BUSINESS IMPACT CARD
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(NavyCardElevated)
        .border(1.dp, WarningAmber.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        .padding(16.dp)
    ) {
      Row(verticalAlignment = Alignment.Top) {
        Icon(
          imageVector = Icons.Default.Warning,
          contentDescription = null,
          tint = WarningAmber,
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "BUSINESS IMPACT",
            color = WarningAmber,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Production may be affected in approximately 6 days.",
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Buffer inventory at Plant 02 covers until Oct 2. Without mitigation, assembly line stops on Day 7.",
            color = TextSecondary,
            fontSize = 12.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    PrimaryActionButton(
      text = "See What Happens Next",
      onClick = onSeeWhatHappensNext,
      icon = Icons.Default.ArrowForward,
      color = SapBlue,
      modifier = Modifier.testTag("see_what_happens_next_button")
    )

    Spacer(modifier = Modifier.height(30.dp))
  }
}

@Composable
private fun BlastMetricCard(
  value: String,
  label: String,
  color: Color,
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
      Text(value, color = color, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
      Spacer(modifier = Modifier.height(2.dp))
      Text(label, color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
    }
  }
}
