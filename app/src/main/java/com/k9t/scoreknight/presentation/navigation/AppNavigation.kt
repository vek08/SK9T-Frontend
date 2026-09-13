package com.k9t.scoreknight.presentation.navigation

import androidx.compose.foundation.layout.Box
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
import com.k9t.scoreknight.presentation.profile.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomTab.HOME.route

    // Create a shared ViewModel scoped to the AppNavigation (NavHost parent)
    val sharedViewModel: HomeViewModel = hiltViewModel()

    Scaffold(
        bottomBar = {
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
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = BottomTab.HOME.route
            ) {
                composable(BottomTab.HOME.route) {
                    HomeScreen(viewModel = sharedViewModel)
                }
                composable(BottomTab.LIVE.route) {
                    LiveScreen(viewModel = sharedViewModel)
                }
                composable(BottomTab.MATCHES.route) {
                    MatchesScreen(viewModel = sharedViewModel)
                }
                composable(BottomTab.FAVORITES.route) {
                    FavoritesScreen(viewModel = sharedViewModel)
                }
                composable(BottomTab.PROFILE.route) {
                    ProfileScreen()
                }
            }
        }
    }
}
