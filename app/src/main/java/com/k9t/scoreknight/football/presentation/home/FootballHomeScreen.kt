package com.k9t.scoreknight.football.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.k9t.scoreknight.football.domain.model.FootballMatch
import com.k9t.scoreknight.presentation.components.AutoScrollingCarousel
import com.k9t.scoreknight.presentation.components.EmptyState
import com.k9t.scoreknight.presentation.home.CricketerBannerSlider
import com.k9t.scoreknight.presentation.components.ErrorState
import com.k9t.scoreknight.presentation.components.LoadingState
import com.k9t.scoreknight.football.presentation.home.FootballCarouselCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FootballHomeScreen(
    viewModel: FootballHomeViewModel = hiltViewModel(),
    onNavigateToMatchDetails: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.loadMatches(isRefresh = true)
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is FootballHomeUiState.Success) {
            val isRefreshing = (uiState as FootballHomeUiState.Success).isRefreshing
            if (!isRefreshing) {
                pullToRefreshState.endRefresh()
            }
        } else if (uiState !is FootballHomeUiState.Loading) {
            pullToRefreshState.endRefresh()
        }
    }

    Scaffold(
        topBar = {
            com.k9t.scoreknight.presentation.components.GlassTopBar(
                title = "ScoreKnight",
                onNotificationClick = { /* Handle notification click */ }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (val state = uiState) {
                is FootballHomeUiState.Loading -> {
                    LoadingState()
                }
                is FootballHomeUiState.Success -> {
                    HomeContent(
                        liveMatches = state.liveMatches,
                        upcomingMatches = state.upcomingMatches,
                        completedMatches = state.completedMatches,
                        favorites = state.favorites,
                        onFavoriteClick = { viewModel.toggleFavorite(it) },
                        onNavigateToMatchDetails = onNavigateToMatchDetails
                    )
                }
                is FootballHomeUiState.Error -> {
                    ErrorState(
                        message = state.message,
                        onRetry = { viewModel.loadMatches() }
                    )
                }
                is FootballHomeUiState.Empty -> {
                    EmptyState()
                }
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun HomeContent(
    liveMatches: List<FootballMatch>,
    upcomingMatches: List<FootballMatch>,
    completedMatches: List<FootballMatch>,
    favorites: Set<String>,
    onFavoriteClick: (String) -> Unit,
    onNavigateToMatchDetails: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp)
    ) {
        // Cricketer Banner Slider Section
        item {
            val banners = remember {
                listOf(
                    com.k9t.scoreknight.domain.model.CricketBanner(
                        id = "1",
                        backgroundUrl = "https://res.cloudinary.com/dwmsjuuox/image/upload/v1789896012/virat_xxjatv.jpg",
                        playerName = "Virat Kohli",
                        tagline = "The Run Machine",
                        destination = "player_profile/kohli"
                    ),
                    com.k9t.scoreknight.domain.model.CricketBanner(
                        id = "2",
                        backgroundUrl = "https://res.cloudinary.com/dwmsjuuox/image/upload/v1789896012/ms_ysvx2v.jpg",
                        playerName = "MS Dhoni",
                        tagline = "Captain Cool",
                        destination = "player_profile/dhoni"
                    ),
                    com.k9t.scoreknight.domain.model.CricketBanner(
                        id = "3",
                        backgroundUrl = "https://res.cloudinary.com/dwmsjuuox/image/upload/v1789896012/rohit_o9sd11.jpg",
                        playerName = "Rohit Sharma",
                        tagline = "Hitman",
                        destination = "player_profile/rohit"
                    )
                )
            }
            
            CricketerBannerSlider(
                banners = banners,
                onBannerClick = { banner -> 
                    // Safe click handling as requested: does not crash if destination is not yet implemented
                    // TODO: Navigate to banner.destination when feature is available
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Live Now Section
        item {
            SectionHeader(title = "LIVE NOW")
            if (liveMatches.isNotEmpty()) {
                AutoScrollingCarousel(
                    items = liveMatches,
                    autoScrollDelay = 4000L,
                    itemContent = { match ->
                        FootballCarouselCard(
                            match = match,
                            isFavorite = favorites.contains(match.id),
                            onFavoriteClick = { onFavoriteClick(match.id) },
                            onClick = { onNavigateToMatchDetails(match.id) }
                        )
                    }
                )
            } else {
                SectionEmptyState(message = "No matches are live right now")
            }
        }

        // Upcoming Section
        item {
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title = "UPCOMING")
            if (upcomingMatches.isNotEmpty()) {
                AutoScrollingCarousel(
                    items = upcomingMatches,
                    autoScrollDelay = 5000L,
                    itemContent = { match ->
                        FootballCarouselCard(
                            match = match,
                            isFavorite = favorites.contains(match.id),
                            onFavoriteClick = { onFavoriteClick(match.id) },
                            onClick = { onNavigateToMatchDetails(match.id) }
                        )
                    }
                )
            } else {
                SectionEmptyState(message = "No upcoming matches scheduled")
            }
        }

        // Recent Results Section
        item {
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title = "RECENT RESULTS")
            if (completedMatches.isNotEmpty()) {
                AutoScrollingCarousel(
                    items = completedMatches,
                    autoScrollDelay = 6000L,
                    itemContent = { match ->
                        FootballCarouselCard(
                            match = match,
                            isFavorite = favorites.contains(match.id),
                            onFavoriteClick = { onFavoriteClick(match.id) },
                            onClick = { onNavigateToMatchDetails(match.id) }
                        )
                    }
                )
            } else {
                SectionEmptyState(message = "No recent results available")
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun SectionEmptyState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}






