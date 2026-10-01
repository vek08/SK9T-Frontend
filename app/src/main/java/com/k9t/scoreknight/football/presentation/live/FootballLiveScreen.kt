package com.k9t.scoreknight.football.presentation.live

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
import com.k9t.scoreknight.football.domain.model.FootballMatch
import com.k9t.scoreknight.presentation.components.EmptyState
import com.k9t.scoreknight.presentation.components.ErrorState
import com.k9t.scoreknight.presentation.components.LoadingState
import com.k9t.scoreknight.football.presentation.components.FootballMatchCardWrapper
import com.k9t.scoreknight.football.presentation.home.FootballHomeUiState
import com.k9t.scoreknight.football.presentation.home.FootballHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FootballLiveScreen(
    viewModel: FootballHomeViewModel,
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
                title = "Live",
                onNotificationClick = null
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
                    val liveMatches = state.liveMatches
                    
                    if (liveMatches.isEmpty()) {
                        EmptyState(
                            title = "No Live Matches Right Now",
                            subtitle = "Check back when matches are in progress."
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 120.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(items = liveMatches, key = { it.id }) { match ->
                                FootballMatchCardWrapper(match = match, isFavorite = state.favorites.contains(match.id), onFavoriteClick = { viewModel.toggleFavorite(match.id) }, onNavigateToMatchDetails = onNavigateToMatchDetails)
                            }
                        }
                    }
                }
                is FootballHomeUiState.Error -> {
                    ErrorState(
                        message = state.message,
                        onRetry = { viewModel.loadMatches() }
                    )
                }
                is FootballHomeUiState.Empty -> {
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





