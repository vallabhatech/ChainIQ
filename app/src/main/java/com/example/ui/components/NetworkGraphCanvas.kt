package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.data.NodeStatus
import com.example.data.SupplyLink
import com.example.data.SupplyNode
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.DisruptionRed
import com.example.ui.theme.HealthyGreen
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.WarningAmber
import kotlin.math.sqrt

@Composable
fun InteractiveNetworkGraph(
  nodes: List<SupplyNode>,
  links: List<SupplyLink>,
  selectedNodeId: String?,
  onNodeTap: (SupplyNode) -> Unit,
  modifier: Modifier = Modifier,
  isBlastRadiusFocus: Boolean = false
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseRadius by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.6f,
    animationSpec = infiniteRepeatable(
      animation = tween(1500, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse"
  )

  val flowPhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 40f,
    animationSpec = infiniteRepeatable(
      animation = tween(1000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "flow"
  )

  Canvas(
    modifier = modifier
      .pointerInput(nodes) {
        detectTapGestures { tapOffset ->
          val width = size.width
          val height = size.height
          // find nearest node within 36dp radius
          var hitNode: SupplyNode? = null
          var minDist = Float.MAX_VALUE
          nodes.forEach { node ->
            val nodeX = node.x * width
            val nodeY = node.y * height
            val dist = sqrt((nodeX - tapOffset.x) * (nodeX - tapOffset.x) + (nodeY - tapOffset.y) * (nodeY - tapOffset.y))
            if (dist < 50f && dist < minDist) {
              minDist = dist
              hitNode = node
            }
          }
          hitNode?.let { onNodeTap(it) }
        }
      }
  ) {
    val canvasWidth = size.width
    val canvasHeight = size.height

    // 1. Draw Links
    val nodeMap = nodes.associateBy { it.id }
    links.forEach { link ->
      val from = nodeMap[link.fromId]
      val to = nodeMap[link.toId]
      if (from != null && to != null) {
        val start = Offset(from.x * canvasWidth, from.y * canvasHeight)
        val end = Offset(to.x * canvasWidth, to.y * canvasHeight)

        val lineColor = when {
          link.isDisrupted -> DisruptionRed.copy(alpha = 0.6f)
          isBlastRadiusFocus -> CyanAccent.copy(alpha = 0.45f)
          else -> NavyBorder.copy(alpha = 0.7f)
        }

        val strokeWidth = if (link.isDisrupted) 3.dp.toPx() else 1.5.dp.toPx()

        drawLine(
          color = lineColor,
          start = start,
          end = end,
          strokeWidth = strokeWidth,
          cap = StrokeCap.Round,
          pathEffect = if (link.isDisrupted) PathEffect.dashPathEffect(floatArrayOf(15f, 15f), flowPhase) else null
        )
      }
    }

    // 2. Draw Nodes
    nodes.forEach { node ->
      val center = Offset(node.x * canvasWidth, node.y * canvasHeight)
      val isSelected = node.id == selectedNodeId
      val baseColor = when (node.status) {
        NodeStatus.DISRUPTED -> DisruptionRed
        NodeStatus.WARNING -> WarningAmber
        NodeStatus.HEALTHY -> HealthyGreen
      }

      // Disrupted / selected pulsing halo
      if (node.status == NodeStatus.DISRUPTED || isSelected) {
        drawCircle(
          color = baseColor.copy(alpha = 0.25f),
          radius = 24.dp.toPx() * (if (node.status == NodeStatus.DISRUPTED) pulseRadius else 1.2f),
          center = center
        )
      }

      // Outer ring
      drawCircle(
        color = if (isSelected) Color.White else baseColor.copy(alpha = 0.8f),
        radius = 14.dp.toPx(),
        center = center,
        style = Stroke(width = if (isSelected) 3.dp.toPx() else 2.dp.toPx())
      )

      // Inner filled circle
      drawCircle(
        color = baseColor,
        radius = 9.dp.toPx(),
        center = center
      )
    }
  }
}
