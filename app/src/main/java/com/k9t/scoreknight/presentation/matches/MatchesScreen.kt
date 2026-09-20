package com.k9t.scoreknight.presentation.matches

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
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

enum class MatchFilter(val displayName: String) {
    ALL("All"),
    LIVE("Live"),
    UPCOMING("Upcoming"),
    COMPLETED("Completed")
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MatchesScreen(
    viewModel: HomeViewModel,
    onNavigateToMatchDetails: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { MatchFilter.values().size })
    val coroutineScope = rememberCoroutineScope()
    
    val categoryChips = listOf("All", "International", "League", "Domestic", "Women's", "More")
    var selectedCategory by remember { mutableStateOf(categoryChips[0]) }

    val pullToRefreshState = rememberPullToRefreshState()

    val isRefreshing = (uiState as? HomeUiState.Success)?.isRefreshing == true
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.loadMatches(isRefresh = true)
        }
    }
    
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            pullToRefreshState.startRefresh()
        } else {
            pullToRefreshState.endRefresh()
        }
    }

    Scaffold(
        topBar = {
            com.k9t.scoreknight.presentation.components.GlassTopBar(
                title = "Matches",
                onNotificationClick = null
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Main Tabs
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                MatchFilter.values().forEachIndexed { index, filter ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { 
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { 
                            Text(
                                text = filter.displayName, 
                                fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal
                            ) 
                        }
                    )
                }
            }

            // Category Filter Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categoryChips) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category, style = MaterialTheme.typography.labelMedium) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            // The refreshable scroll area, below the tabs/chips
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
            ) {
                when (val state = uiState) {
                    is HomeUiState.Loading -> {
                        LoadingState()
                    }
                    is HomeUiState.Success -> {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            val currentTabFilter = MatchFilter.values()[page]

                            var filteredMatches = when (currentTabFilter) {
                                MatchFilter.ALL -> state.matches
                                MatchFilter.LIVE -> state.liveMatches
                                MatchFilter.UPCOMING -> state.upcomingMatches
                                MatchFilter.COMPLETED -> state.completedMatches
                            }
                            
                            if (selectedCategory != "All") {
                                filteredMatches = filteredMatches.filter { match ->
                                    val allText = "${match.teamA} ${match.teamB} ${match.venue} ${match.status}".lowercase()
                                    when (selectedCategory) {
                                        "Women's" -> allText.contains("women")
                                        "Domestic", "League" -> !allText.contains("international") && !allText.contains("test")
                                        "International" -> allText.contains("test") || allText.contains("odi") || allText.contains("t20i")
                                        else -> true
                                    }
                                }
                            }
                            
                            if (filteredMatches.isEmpty()) {
                                EmptyState(
                                    title = "No Matches Found",
                                    subtitle = "No matches match the selected filters."
                                )
                            } else {
                                val groupedMatches = filteredMatches.groupBy { 
                                    if (it.matchDate.isNotBlank()) it.matchDate else "Other Matches"
                                }

                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 120.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    groupedMatches.forEach { (dateHeader, matches) ->
                                        item {
                                            SectionHeader(title = dateHeader)
                                        }
                                        
                                        items(items = matches, key = { it.matchId }) { match ->
                                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
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
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = pullToRefreshState,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "View All >",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { /* View All */ }
        )
    }
}

