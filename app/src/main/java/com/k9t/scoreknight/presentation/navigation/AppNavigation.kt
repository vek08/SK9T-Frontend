package com.k9t.scoreknight.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.k9t.scoreknight.auth.presentation.AuthEventManager
import androidx.compose.runtime.LaunchedEffect
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
import com.k9t.scoreknight.auth.presentation.AuthViewModel
import com.k9t.scoreknight.auth.presentation.AuthScreen
import com.k9t.scoreknight.auth.presentation.SignupScreen
import com.k9t.scoreknight.presentation.landing.ScoreKnightLandingScreen
import com.k9t.scoreknight.football.presentation.home.FootballHomeScreen
import com.k9t.scoreknight.football.presentation.matches.FootballMatchesScreen
import com.k9t.scoreknight.football.presentation.live.FootballLiveScreen
import com.k9t.scoreknight.football.presentation.favorites.FootballFavoritesScreen
import com.k9t.scoreknight.football.presentation.details.FootballMatchDetailsScreen
import com.k9t.scoreknight.football.presentation.standings.FootballStandingsScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.k9t.scoreknight.football.presentation.details.FootballMatchDetailsViewModel

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomTab.HOME.route

    // Create a shared ViewModel scoped to the AppNavigation (NavHost parent)
    val sharedViewModel: HomeViewModel = hiltViewModel()
    val sharedFootballViewModel: com.k9t.scoreknight.football.presentation.home.FootballHomeViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()

        LaunchedEffect(Unit) {
        AuthEventManager.authRequiredFlow.collect {
            navController.navigate("login")
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "landing",
            modifier = Modifier.fillMaxSize()
        ) {
            
            composable("login") {
                AuthScreen(
                    viewModel = authViewModel,
                    onNavigateToSignup = { navController.navigate("signup") },
                    onSuccess = { navController.popBackStack() }
                )
            }
            composable("signup") {
                SignupScreen(
                    viewModel = authViewModel,
                    onNavigateToLogin = { navController.popBackStack() },
                    onSuccess = { 
                        navController.popBackStack()
                        // maybe pop back to the previous screen that requested auth
                    }
                )
            }

            composable("landing") {
                ScoreKnightLandingScreen(
                    onNavigateToCricket = { navController.navigate(BottomTab.HOME.route) },
                    onNavigateToFootball = { navController.navigate("football_home") }
                )
            }
            composable("football_home") {
                FootballHomeScreen(viewModel = sharedFootballViewModel,
                    onNavigateToMatchDetails = { id -> navController.navigate("football_match/$id") }
                )
            }
            composable("football_matches") {
                FootballMatchesScreen(viewModel = sharedFootballViewModel,
                    onNavigateToMatchDetails = { id -> navController.navigate("football_match/$id") }
                )
            }
            composable("football_live") {
                FootballLiveScreen(viewModel = sharedFootballViewModel,
                    onNavigateToMatchDetails = { id -> navController.navigate("football_match/$id") }
                )
            }
            composable("football_favorites") {
                FootballFavoritesScreen(viewModel = sharedFootballViewModel,
                    onNavigateToMatchDetails = { id -> navController.navigate("football_match/$id") }
                )
            }
            composable("football_profile") {
                ProfileScreen(
                    authViewModel = authViewModel,
                    onNavigateToSignup = { navController.navigate("signup") }
                )
            }
            composable("football_standings") {
                FootballStandingsScreen()
            }
            composable(
                "football_match/{matchId}",
                arguments = listOf(navArgument("matchId") { type = NavType.StringType })
            ) {
                val footballMatchDetailsViewModel: FootballMatchDetailsViewModel = hiltViewModel()
                FootballMatchDetailsScreen(
                    viewModel = footballMatchDetailsViewModel,
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
                ProfileScreen(
                    authViewModel = authViewModel,
                    onNavigateToSignup = { navController.navigate("signup") }
                )
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
        
        val isFootballRoute = currentRoute.startsWith("football_")
        val isDetailRoute = currentRoute.startsWith("match/") || currentRoute.startsWith("football_match/")
        val shouldShowBottomBar = (currentRoute in BottomTab.values().map { it.route } || isFootballRoute) && !isDetailRoute && currentRoute != "landing"
        
        val bottomBarRoute = when {
            currentRoute == "football_home" -> "home"
            currentRoute == "football_matches" -> "matches"
            currentRoute == "football_live" -> "live"
            currentRoute == "football_favorites" -> "favorites"
            currentRoute == "football_profile" -> "profile"
            else -> currentRoute
        }

        if (shouldShowBottomBar) {
            ScoreKnightBottomBar(
                currentRoute = bottomBarRoute,
                onNavigate = { tab ->
                    val targetRoute = if (isFootballRoute) {
                        when (tab) {
                            BottomTab.HOME -> "football_home"
                            BottomTab.MATCHES -> "football_matches"
                            BottomTab.LIVE -> "football_live"
                            BottomTab.FAVORITES -> "football_favorites"
                            BottomTab.PROFILE -> "football_profile"
                        }
                    } else {
                        tab.route
                    }
                    navController.navigate(targetRoute) {
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


