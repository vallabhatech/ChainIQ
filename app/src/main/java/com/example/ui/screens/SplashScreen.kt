package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.NavyBackground
import com.example.ui.theme.NavySurface
import com.example.ui.theme.SapBlue
import com.example.ui.theme.SapBlueLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  onNavigateToHome: () -> Unit
) {
  val logoAlpha = remember { Animatable(0f) }
  val networkProgress = remember { Animatable(0f) }

  val infiniteTransition = rememberInfiniteTransition(label = "halo")
  val haloScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.35f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "haloScale"
  )

  LaunchedEffect(Unit) {
    networkProgress.animateTo(1f, animationSpec = tween(1000, easing = FastOutSlowInEasing))
    logoAlpha.animateTo(1f, animationSpec = tween(700))
    delay(600)
    onNavigateToHome()
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.radialGradient(
          colors = listOf(NavySurface, NavyBackground),
          center = Offset.Unspecified,
          radius = 1200f
        )
      )
      .clickable { onNavigateToHome() },
    contentAlignment = Alignment.Center
  ) {
    // Network nodes connection canvas
    Canvas(modifier = Modifier.size(280.dp)) {
      val center = Offset(size.width / 2f, size.height / 2f)
      val p = networkProgress.value

      val nodeOffsets = listOf(
        Offset(center.x - 90f * p, center.y - 80f * p),
        Offset(center.x + 90f * p, center.y - 70f * p),
        Offset(center.x - 100f * p, center.y + 60f * p),
        Offset(center.x + 95f * p, center.y + 70f * p),
        Offset(center.x, center.y - 110f * p),
        Offset(center.x, center.y + 110f * p)
      )

      // Draw glowing lines to center
      nodeOffsets.forEach { pos ->
        drawLine(
          color = CyanAccent.copy(alpha = 0.4f * p),
          start = center,
          end = pos,
          strokeWidth = 2.dp.toPx()
        )
        // Interconnected ring
        drawCircle(
          color = SapBlueLight.copy(alpha = 0.8f * p),
          radius = 5.dp.toPx(),
          center = pos
        )
      }

      // Center glowing pulse
      drawCircle(
        color = CyanAccent.copy(alpha = 0.15f * p),
        radius = 56.dp.toPx() * haloScale,
        center = center
      )
      drawCircle(
        color = SapBlue,
        radius = 42.dp.toPx(),
        center = center
      )
      drawCircle(
        color = CyanAccent,
        radius = 42.dp.toPx(),
        center = center,
        style = Stroke(width = 2.dp.toPx())
      )
    }

    // Logo & Subtitle
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .padding(top = 220.dp)
        .alpha(logoAlpha.value)
    ) {
      Text(
        text = "RESILIGRAPH",
        color = TextPrimary,
        fontSize = 28.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = 4.sp
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "AI Crisis Navigator",
        color = CyanAccent,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 1.sp
      )
      Spacer(modifier = Modifier.height(18.dp))
      Text(
        text = "Connecting supply network intelligence...",
        color = TextMuted,
        fontSize = 12.sp
      )
    }
  }
}
