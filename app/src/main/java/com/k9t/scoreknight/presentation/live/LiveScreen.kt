package com.k9t.scoreknight.presentation.live

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.k9t.scoreknight.domain.model.CricketMatch
import com.k9t.scoreknight.presentation.components.EmptyState
import com.k9t.scoreknight.presentation.components.ErrorState
import com.k9t.scoreknight.presentation.components.LoadingState
import com.k9t.scoreknight.presentation.components.MatchCard
import com.k9t.scoreknight.presentation.home.HomeUiState
import com.k9t.scoreknight.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveScreen(
    viewModel: HomeViewModel,
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
                        text = "Live Matches",
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
                    val liveMatches = state.liveMatches
                    
                    if (liveMatches.isEmpty()) {
                        EmptyState(
                            title = "No Live Matches Right Now",
                            subtitle = "Check back when matches are in progress."
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(items = liveMatches, key = { it.matchId }) { match ->
                                MatchCard(
                                    match = match,
                                    isFavorite = state.favorites.contains(match.matchId),
                                    onFavoriteClick = { viewModel.toggleFavorite(match.matchId) },
                                    onClick = { onNavigateToMatchDetails(match.matchId) }
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
                    EmptyState(
                        title = "No Live Matches Right Now",
                        subtitle = "Check back when matches are in progress."
                    )
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
