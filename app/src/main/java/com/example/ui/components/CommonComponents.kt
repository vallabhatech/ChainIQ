package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.RecoveryOption
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DisruptionRed
import com.example.ui.theme.DisruptionRedGlow
import com.example.ui.theme.HealthyGreen
import com.example.ui.theme.HealthyGreenGlow
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyBorderSubtle
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.SapBlue
import com.example.ui.theme.SapBlueLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningAmberGlow

@Composable
fun StatusChip(
  text: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(20.dp))
      .background(color.copy(alpha = 0.15f))
      .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
      .padding(horizontal = 10.dp, vertical = 4.dp)
  ) {
    Text(
      text = text,
      color = color,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      letterSpacing = 0.5.sp
    )
  }
}

@Composable
fun MetricCard(
  title: String,
  value: String,
  subtitle: String? = null,
  icon: ImageVector? = null,
  accentColor: Color = CyanAccent,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .background(NavyCard)
      .border(1.dp, NavyBorder, RoundedCornerShape(16.dp))
      .padding(16.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          color = TextSecondary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium
        )
        if (icon != null) {
          Icon(
            imageVector = icon,
            contentDescription = title,
            tint = accentColor,
            modifier = Modifier.size(18.dp)
          )
        }
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = value,
        color = TextPrimary,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
      )
      if (subtitle != null) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = subtitle,
          color = TextMuted,
          fontSize = 11.sp
        )
      }
    }
  }
}

@Composable
fun ResilienceScoreWidget(
  score: Int,
  statusLabel: String = "Stable",
  supportingText: String = "1 emerging risk detected",
  modifier: Modifier = Modifier,
  onClick: (() -> Unit)? = null
) {
  val scoreProgress = score / 100f
  val strokeColor = when {
    score >= 75 -> HealthyGreen
    score >= 60 -> WarningAmber
    else -> DisruptionRed
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(
        Brush.verticalGradient(
          colors = listOf(NavyCardElevated, NavyCard)
        )
      )
      .border(1.dp, NavyBorder, RoundedCornerShape(20.dp))
      .clickable(enabled = onClick != null) { onClick?.invoke() }
      .padding(20.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Supply Resilience",
            color = TextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
          )
          Spacer(modifier = Modifier.width(8.dp))
          StatusChip(text = statusLabel, color = strokeColor)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.Bottom) {
          Text(
            text = "$score",
            color = TextPrimary,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold
          )
          Text(
            text = " / 100",
            color = TextMuted,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
          )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = supportingText,
          color = if (score < 75) WarningAmber else TextSecondary,
          fontSize = 12.sp
        )
      }

      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(76.dp)
      ) {
        CircularProgressIndicator(
          progress = { 1f },
          modifier = Modifier.size(76.dp),
          color = NavyBorderSubtle,
          strokeWidth = 7.dp,
        )
        CircularProgressIndicator(
          progress = { scoreProgress },
          modifier = Modifier.size(76.dp),
          color = strokeColor,
          strokeWidth = 7.dp,
        )
        Text(
          text = "${(scoreProgress * 100).toInt()}%",
          color = TextPrimary,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

@Composable
fun AlertCard(
  title: String,
  description: String,
  shortageDays: Int,
  onActionClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "alert_pulse")
  val borderAlpha by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 0.9f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "alpha"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(
        Brush.verticalGradient(
          colors = listOf(
            DisruptionRed.copy(alpha = 0.12f),
            NavyCard
          )
        )
      )
      .border(1.5.dp, DisruptionRed.copy(alpha = borderAlpha), RoundedCornerShape(20.dp))
      .padding(18.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(10.dp)
              .clip(CircleShape)
              .background(DisruptionRed)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "ACTIVE ALERT",
            color = DisruptionRed,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
        }
        StatusChip(text = "CRITICAL", color = DisruptionRed)
      }

      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = title,
        color = TextPrimary,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = description,
        color = TextSecondary,
        fontSize = 13.sp
      )

      Spacer(modifier = Modifier.height(14.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = WarningAmber,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "$shortageDays days until projected shortage",
            color = WarningAmber,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )
        }

        Button(
          onClick = onActionClick,
          colors = ButtonDefaults.buttonColors(containerColor = DisruptionRed),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("view_impact_button")
        ) {
          Text("View Impact", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
          Spacer(modifier = Modifier.width(4.dp))
          Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
        }
      }
    }
  }
}

@Composable
fun RecoveryPlanCard(
  option: RecoveryOption,
  isSelected: Boolean,
  onSelect: () -> Unit,
  onExplore: () -> Unit,
  modifier: Modifier = Modifier
) {
  val borderColor = if (isSelected) CyanAccent else if (option.isRecommended) SapBlue else NavyBorder

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(18.dp))
      .background(if (isSelected) NavyCardElevated else NavyCard)
      .border(if (isSelected) 1.5.dp else 1.dp, borderColor, RoundedCornerShape(18.dp))
      .clickable { onSelect() }
      .padding(16.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = option.title,
            color = if (option.isRecommended) CyanAccent else TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Text(
            text = option.subtitle,
            color = TextPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
          )
        }
        if (option.statusBadge != null) {
          StatusChip(
            text = option.statusBadge,
            color = if (option.isRecommended) HealthyGreen else CyanAccent
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 4 Metrics Grid: Cost, Recovery, Risk, Carbon
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        MetricItem(label = "💰 Cost", value = option.costLabel)
        MetricItem(label = "⏱ Recovery", value = "${option.recoveryDays} days")
        MetricItem(label = "🛡 Risk", value = option.riskLabel)
        MetricItem(label = "🌱 Carbon", value = option.carbonLabel)
      }

      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = option.description,
        color = TextSecondary,
        fontSize = 12.sp,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(14.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        OutlinedButton(
          onClick = onExplore,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            contentColor = CyanAccent
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.5f)),
          modifier = Modifier.testTag("explore_${option.id}")
        ) {
          Text("Explore", fontSize = 12.sp)
          Spacer(modifier = Modifier.width(4.dp))
          Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
        }
      }
    }
  }
}

@Composable
private fun MetricItem(label: String, value: String) {
  Column(horizontalAlignment = Alignment.Start) {
    Text(label, color = TextMuted, fontSize = 11.sp)
    Spacer(modifier = Modifier.height(2.dp))
    Text(value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
  }
}

@Composable
fun PrimaryActionButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  icon: ImageVector? = null,
  color: Color = SapBlue,
  enabled: Boolean = true
) {
  Button(
    onClick = onClick,
    enabled = enabled,
    modifier = modifier
      .fillMaxWidth()
      .height(52.dp)
      .testTag("action_button_${text.lowercase().replace(" ", "_")}"),
    colors = ButtonDefaults.buttonColors(
      containerColor = color,
      disabledContainerColor = NavyCard
    ),
    shape = RoundedCornerShape(14.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      Text(
        text = text,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = if (enabled) TextPrimary else TextMuted
      )
      if (icon != null) {
        Spacer(modifier = Modifier.width(8.dp))
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = TextPrimary)
      }
    }
  }
}
