package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.navigation.Routes
import com.example.ui.screens.ApprovalScreen
import com.example.ui.screens.ChallengeAiScreen
import com.example.ui.screens.HiddenDependencyScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ImpactRadiusScreen
import com.example.ui.screens.ImpactTimeMachineScreen
import com.example.ui.screens.IncidentAnalysisScreen
import com.example.ui.screens.PolicyBrainScreen
import com.example.ui.screens.RecommendationScreen
import com.example.ui.screens.RecoveryComparisonScreen
import com.example.ui.screens.RecoverySimulatorScreen
import com.example.ui.screens.RecoverySuccessScreen
import com.example.ui.screens.ReportDisruptionScreen
import com.example.ui.screens.ResilienceScoreScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.SupplyGraphScreen
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.NavyBackground
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavySurface
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SapBlue
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.ResiliGraphViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        ResiliGraphApp()
      }
    }
  }
}

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
  object Home : BottomNavItem(Routes.HOME, "Home", Icons.Default.Home)
  object Report : BottomNavItem(Routes.REPORT_DISRUPTION, "Report", Icons.Default.Mic)
  object Network : BottomNavItem(Routes.SUPPLY_GRAPH, "Network", Icons.Default.Hub)
  object Simulator : BottomNavItem(Routes.RECOVERY_SIMULATOR, "Simulator", Icons.Default.AltRoute)
  object Settings : BottomNavItem(Routes.SETTINGS, "Settings", Icons.Default.Settings)
}

@Composable
fun ResiliGraphApp() {
  val navController = rememberNavController()
  val viewModel: ResiliGraphViewModel = viewModel()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Report,
    BottomNavItem.Network,
    BottomNavItem.Simulator,
    BottomNavItem.Settings
  )

  val showBottomBar = currentRoute in listOf(
    Routes.HOME,
    Routes.REPORT_DISRUPTION,
    Routes.SUPPLY_GRAPH,
    Routes.RECOVERY_SIMULATOR,
    Routes.SETTINGS
  )

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyBackground),
    containerColor = NavyBackground,
    bottomBar = {
      AnimatedVisibility(
        visible = showBottomBar,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
      ) {
        NavigationBar(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .border(1.dp, NavyBorder, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
          containerColor = NavySurface,
          tonalElevation = 8.dp
        ) {
          bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
              selected = selected,
              onClick = {
                if (currentRoute != item.route) {
                  navController.navigate(item.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                      saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                  }
                }
              },
              icon = {
                Icon(
                  imageVector = item.icon,
                  contentDescription = item.title,
                  modifier = Modifier.size(20.dp)
                )
              },
              label = {
                Text(
                  text = item.title,
                  fontSize = 10.sp
                )
              },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CyanAccent,
                selectedTextColor = CyanAccent,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted,
                indicatorColor = SapBlue.copy(alpha = 0.25f)
              ),
              modifier = Modifier.testTag("nav_tab_${item.route}")
            )
          }
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(NavyBackground)
    ) {
      NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = Modifier.fillMaxSize()
      ) {
        composable(Routes.SPLASH) {
          SplashScreen(
            onNavigateToHome = {
              navController.navigate(Routes.HOME) {
                popUpTo(Routes.SPLASH) { inclusive = true }
              }
            }
          )
        }

        composable(Routes.HOME) {
          HomeScreen(
            viewModel = viewModel,
            onNavigate = { route -> navController.navigate(route) }
          )
        }

        composable(Routes.REPORT_DISRUPTION) {
          ReportDisruptionScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onAnalyze = { navController.navigate(Routes.INCIDENT_ANALYSIS) }
          )
        }

        composable(Routes.INCIDENT_ANALYSIS) {
          IncidentAnalysisScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onSeeImpactRadius = { navController.navigate(Routes.IMPACT_RADIUS) }
          )
        }

        composable(Routes.IMPACT_RADIUS) {
          ImpactRadiusScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onSeeWhatHappensNext = { navController.navigate(Routes.IMPACT_TIME_MACHINE) }
          )
        }

        composable(Routes.IMPACT_TIME_MACHINE) {
          ImpactTimeMachineScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onSimulateRecovery = { navController.navigate(Routes.RECOVERY_SIMULATOR) }
          )
        }

        composable(Routes.RECOVERY_SIMULATOR) {
          RecoverySimulatorScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onCompareAll = { navController.navigate(Routes.RECOVERY_COMPARISON) },
            onExploreOption = { navController.navigate(Routes.RECOMMENDATION) }
          )
        }

        composable(Routes.RECOVERY_COMPARISON) {
          RecoveryComparisonScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onViewRecommendation = { navController.navigate(Routes.RECOMMENDATION) }
          )
        }

        composable(Routes.RECOMMENDATION) {
          RecommendationScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onCompareAgain = { navController.navigate(Routes.RECOVERY_COMPARISON) },
            onSimulate = { navController.navigate(Routes.RECOVERY_SIMULATOR) },
            onChallengeAi = { navController.navigate(Routes.CHALLENGE_AI) },
            onProceedToApproval = { navController.navigate(Routes.APPROVAL) }
          )
        }

        composable(Routes.CHALLENGE_AI) {
          ChallengeAiScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onProceedToApproval = { navController.navigate(Routes.APPROVAL) }
          )
        }

        composable(Routes.HIDDEN_DEPENDENCY) {
          HiddenDependencyScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onExploreDependency = { navController.navigate(Routes.SUPPLY_GRAPH) }
          )
        }

        composable(Routes.SUPPLY_GRAPH) {
          SupplyGraphScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onInvestigateNode = { navController.navigate(Routes.INCIDENT_ANALYSIS) }
          )
        }

        composable(Routes.RESILIENCE_SCORE) {
          ResilienceScoreScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onImproveResilience = { navController.navigate(Routes.RECOVERY_SIMULATOR) }
          )
        }

        composable(Routes.POLICY_BRAIN) {
          PolicyBrainScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() }
          )
        }

        composable(Routes.APPROVAL) {
          ApprovalScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onApproved = {
              navController.navigate(Routes.RECOVERY_SUCCESS) {
                popUpTo(Routes.APPROVAL) { inclusive = true }
              }
            }
          )
        }

        composable(Routes.RECOVERY_SUCCESS) {
          RecoverySuccessScreen(
            viewModel = viewModel,
            onNavigateHome = {
              navController.navigate(Routes.HOME) {
                popUpTo(Routes.HOME) { inclusive = true }
              }
            }
          )
        }

        composable(Routes.SETTINGS) {
          SettingsScreen(
            viewModel = viewModel,
            onNavigateBack = { navController.popBackStack() },
            onOpenPolicyBrain = { navController.navigate(Routes.POLICY_BRAIN) }
          )
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}
