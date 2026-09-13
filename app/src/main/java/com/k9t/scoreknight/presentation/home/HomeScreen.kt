package com.k9t.scoreknight.presentation.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.k9t.scoreknight.domain.model.CricketMatch
import com.k9t.scoreknight.presentation.components.AutoScrollingCarousel
import com.k9t.scoreknight.presentation.components.EmptyState
import com.k9t.scoreknight.presentation.components.ErrorState
import com.k9t.scoreknight.presentation.components.LoadingState
import com.k9t.scoreknight.presentation.components.MatchCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
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
        if (uiState is HomeUiState.Success) {
            val isRefreshing = (uiState as HomeUiState.Success).isRefreshing
            if (!isRefreshing) {
                pullToRefreshState.endRefresh()
            }
        } else if (uiState !is HomeUiState.Loading) {
            pullToRefreshState.endRefresh()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "ScoreKnight",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
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
                is HomeUiState.Loading -> {
                    LoadingState()
                }
                is HomeUiState.Success -> {
                    HomeContent(
                        matches = state.matches,
                        favorites = state.favorites,
                        onFavoriteClick = viewModel::toggleFavorite,
                        onNavigateToMatchDetails = onNavigateToMatchDetails
                    )
                }
                is HomeUiState.Error -> {
                    ErrorState(
                        message = state.message,
                        onRetry = { viewModel.loadMatches() }
                    )
                }
                is HomeUiState.Empty -> {
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
    matches: List<CricketMatch>,
    favorites: Set<String>,
    onFavoriteClick: (String) -> Unit,
    onNavigateToMatchDetails: (String) -> Unit
) {
    val liveMatches = matches.filter { it.status.lowercase().contains("live") || it.status.lowercase().contains("inning") }
    val upcomingMatches = matches.filter { it.status.lowercase().contains("upcoming") || it.status.lowercase().contains("yet to begin") }
    val completedMatches = matches.filterNot { liveMatches.contains(it) || upcomingMatches.contains(it) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Live Now Section
        item {
            SectionHeader(title = "LIVE NOW")
            if (liveMatches.isNotEmpty()) {
                AutoScrollingCarousel(
                    items = liveMatches,
                    autoScrollDelay = 4000L,
                    itemContent = { match ->
                        MatchCard(
                            match = match,
                            isFavorite = favorites.contains(match.matchId),
                            onFavoriteClick = { onFavoriteClick(match.matchId) },
                            onClick = { onNavigateToMatchDetails(match.matchId) }
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
                        MatchCard(
                            match = match,
                            isFavorite = favorites.contains(match.matchId),
                            onFavoriteClick = { onFavoriteClick(match.matchId) },
                            onClick = { onNavigateToMatchDetails(match.matchId) }
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
                        MatchCard(
                            match = match,
                            isFavorite = favorites.contains(match.matchId),
                            onFavoriteClick = { onFavoriteClick(match.matchId) },
                            onClick = { onNavigateToMatchDetails(match.matchId) }
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
