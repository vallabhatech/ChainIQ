package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ApprovalScreen(
  viewModel: ResiliGraphViewModel,
  onNavigateBack: () -> Unit,
  onApproved: () -> Unit
) {
  var isApprovedAnim by remember { mutableStateOf(false) }
  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

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
        text = "Recovery Approval",
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Joule / SAP Header Badge
    Row(
      modifier = Modifier
        .clip(RoundedCornerShape(8.dp))
        .background(SapBlue.copy(alpha = 0.25f))
        .border(1.dp, SapBlue.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
        .padding(horizontal = 10.dp, vertical = 5.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(Icons.Default.Security, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(14.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "Prototype SAP / Joule workflow",
        color = CyanAccent,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Text(
      text = "Execute Decision",
      color = TextPrimary,
      fontSize = 24.sp,
      fontWeight = FontWeight.ExtraBold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Human-in-the-loop executive sign-off",
      color = TextSecondary,
      fontSize = 13.sp
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Enterprise Approval Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(NavyCard)
        .border(1.5.dp, SapBlue.copy(alpha = 0.7f), RoundedCornerShape(20.dp))
        .padding(20.dp)
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Supplier disruption detected",
            color = WarningAmber,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
          StatusChip(text = "PENDING SIGN-OFF", color = WarningAmber)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Recommendation",
          color = TextMuted,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        )
        Text(
          text = "Shift 40% volume to Supplier B",
          color = TextPrimary,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Impact metrics grid
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NavyCardElevated)
            .padding(14.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text("Delivery Adjustment:", color = TextSecondary, fontSize = 12.sp)
              Text("+2 days delivery", color = HealthyGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text("Estimated Incremental Cost:", color = TextSecondary, fontSize = 12.sp)
              Text("+$8,500 estimated cost", color = WarningAmber, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text("Overall Network Risk:", color = TextSecondary, fontSize = 12.sp)
              Text("Risk reduced (82 → 78)", color = HealthyGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text("Carbon Footprint Impact:", color = TextSecondary, fontSize = 12.sp)
              Text("Moderate (+4% CO₂e)", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Human-in-the-loop motto
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
        ) {
          Text(
            text = "“ResiliGraph recommends. You decide.”",
            color = CyanAccent,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(26.dp))

    if (!isApprovedAnim) {
      // 3 Action Buttons: APPROVE, REVIEW, REJECT
      Button(
        onClick = {
          isApprovedAnim = true
          viewModel.approvePlan()
          scope.launch {
            delay(1200)
            onApproved()
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("approve_button"),
        colors = ButtonDefaults.buttonColors(containerColor = HealthyGreen),
        shape = RoundedCornerShape(14.dp)
      ) {
        Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("APPROVE RECOMMENDATION", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Black)
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = {
            scope.launch { snackbarHostState.showSnackbar("Routed to Supply Chain Review Board.") }
          },
          modifier = Modifier.weight(1f).height(46.dp).testTag("review_button"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = WarningAmber),
          border = androidx.compose.foundation.BorderStroke(1.dp, WarningAmber)
        ) {
          Icon(Icons.Default.HelpOutline, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("REVIEW", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }

        OutlinedButton(
          onClick = {
            scope.launch { snackbarHostState.showSnackbar("Plan rejected. Reverting to status quo.") }
          },
          modifier = Modifier.weight(1f).height(46.dp).testTag("reject_button"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = DisruptionRed),
          border = androidx.compose.foundation.BorderStroke(1.dp, DisruptionRed)
        ) {
          Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("REJECT", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
      }
    } else {
      // Animated Confirmation Box
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(HealthyGreen.copy(alpha = 0.15f))
          .border(2.dp, HealthyGreen, RoundedCornerShape(16.dp))
          .padding(24.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Box(
            modifier = Modifier.size(54.dp).clip(CircleShape).background(HealthyGreen),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(32.dp))
          }
          Spacer(modifier = Modifier.height(12.dp))
          Text("APPROVAL DISPATCHED", color = HealthyGreen, fontSize = 16.sp, fontWeight = FontWeight.Black)
          Spacer(modifier = Modifier.height(4.dp))
          Text("Writing Purchase Order to SAP S/4HANA...", color = TextSecondary, fontSize = 12.sp)
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
    SnackbarHost(hostState = snackbarHostState)
    Spacer(modifier = Modifier.height(30.dp))
  }
}
