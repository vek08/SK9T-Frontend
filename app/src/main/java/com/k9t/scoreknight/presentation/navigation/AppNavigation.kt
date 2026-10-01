package com.k9t.scoreknight.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.k9t.scoreknight.presentation.components.BottomTab
import com.k9t.scoreknight.presentation.components.ScoreKnightBottomBar
import com.k9t.scoreknight.presentation.favorites.FavoritesScreen
import com.k9t.scoreknight.presentation.home.HomeScreen
import com.k9t.scoreknight.presentation.home.HomeViewModel
import com.k9t.scoreknight.presentation.live.LiveScreen
import com.k9t.scoreknight.presentation.matches.MatchesScreen
import com.k9t.scoreknight.presentation.matchdetails.MatchDetailsScreen
import com.k9t.scoreknight.presentation.profile.ProfileScreen
import com.k9t.scoreknight.presentation.landing.ScoreKnightLandingScreen
import com.k9t.scoreknight.presentation.football.FootballHomeScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomTab.HOME.route

    // Create a shared ViewModel scoped to the AppNavigation (NavHost parent)
    val sharedViewModel: HomeViewModel = hiltViewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "landing",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("landing") {
                ScoreKnightLandingScreen(
                    onNavigateToCricket = { navController.navigate(BottomTab.HOME.route) },
                    onNavigateToFootball = { navController.navigate("football_home") }
                )
            }
            composable("football_home") {
                FootballHomeScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(BottomTab.HOME.route) {
                HomeScreen(
                    viewModel = sharedViewModel,
                    onNavigateToMatchDetails = { matchId -> navController.navigate("match/$matchId") }
                )
            }
                composable(BottomTab.LIVE.route) {
                    LiveScreen(
                        viewModel = sharedViewModel,
                        onNavigateToMatchDetails = { matchId -> navController.navigate("match/$matchId") }
                    )
                }
                composable(BottomTab.MATCHES.route) {
                    MatchesScreen(
                        viewModel = sharedViewModel,
                        onNavigateToMatchDetails = { matchId -> navController.navigate("match/$matchId") }
                    )
                }
                composable(BottomTab.FAVORITES.route) {
                    FavoritesScreen(
                        viewModel = sharedViewModel,
                        onNavigateToMatchDetails = { matchId -> navController.navigate("match/$matchId") }
                    )
                }
                composable(BottomTab.PROFILE.route) {
                    ProfileScreen()
                }
                composable(
                    route = "match/{matchId}",
                    arguments = listOf(navArgument("matchId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val matchDetailsViewModel: com.k9t.scoreknight.presentation.matchdetails.MatchDetailsViewModel = hiltViewModel()
                    MatchDetailsScreen(
                        viewModel = matchDetailsViewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        
        val shouldShowBottomBar = currentRoute in BottomTab.values().map { it.route }
        if (shouldShowBottomBar) {
            ScoreKnightBottomBar(
                currentRoute = currentRoute,
                onNavigate = { tab ->
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.align(androidx.compose.ui.Alignment.BottomCenter)
            )
        }
    }
}
