package com.k9t.scoreknight.presentation.matches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.k9t.scoreknight.presentation.components.EmptyState
import com.k9t.scoreknight.presentation.components.ErrorState
import com.k9t.scoreknight.presentation.components.LoadingState
import com.k9t.scoreknight.presentation.components.MatchCard
import com.k9t.scoreknight.presentation.home.HomeUiState
import com.k9t.scoreknight.presentation.home.HomeViewModel

enum class MatchFilter(val displayName: String) {
    ALL("All"),
    LIVE("Live"),
    UPCOMING("Upcoming"),
    COMPLETED("Completed")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesScreen(
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()
    var selectedFilter by remember { mutableStateOf(MatchFilter.ALL) }

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
                        text = "All Matches",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Filter Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(MatchFilter.values()) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter.displayName) }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
            ) {
                when (val state = uiState) {
                    is HomeUiState.Loading -> {
                        LoadingState()
                    }
                    is HomeUiState.Success -> {
                        val filteredMatches = state.matches.filter { match ->
                            val status = match.status.lowercase()
                            when (selectedFilter) {
                                MatchFilter.ALL -> true
                                MatchFilter.LIVE -> status.contains("live") || status.contains("inning")
                                MatchFilter.UPCOMING -> status.contains("upcoming") || status.contains("yet to begin")
                                MatchFilter.COMPLETED -> !status.contains("live") && !status.contains("inning") && !status.contains("upcoming") && !status.contains("yet to begin")
                            }
                        }
                        
                        if (filteredMatches.isEmpty()) {
                            EmptyState(
                                title = "No Matches Found",
                                subtitle = "No matches match the selected filter."
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(items = filteredMatches, key = { it.matchId }) { match ->
                                    MatchCard(
                                        match = match,
                                        isFavorite = state.favorites.contains(match.matchId),
                                        onFavoriteClick = { viewModel.toggleFavorite(match.matchId) }
                                    )
                                }
                            }
                        }
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
}
