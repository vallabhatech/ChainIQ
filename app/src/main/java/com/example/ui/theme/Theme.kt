package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  primary = SapBlue,
  onPrimary = TextPrimary,
  primaryContainer = NavyCardElevated,
  onPrimaryContainer = CyanAccent,
  secondary = CyanAccent,
  onSecondary = NavyBackground,
  secondaryContainer = NavyCard,
  onSecondaryContainer = TextPrimary,
  tertiary = HealthyGreen,
  onTertiary = TextPrimary,
  background = NavyBackground,
  onBackground = TextPrimary,
  surface = NavySurface,
  onSurface = TextPrimary,
  surfaceVariant = NavyCard,
  onSurfaceVariant = TextSecondary,
  outline = NavyBorder,
  error = DisruptionRed,
  onError = TextPrimary
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = DarkColorScheme,
    typography = Typography,
    content = content
  )
}
