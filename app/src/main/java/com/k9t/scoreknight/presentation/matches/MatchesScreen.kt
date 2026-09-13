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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesScreen(
    viewModel: HomeViewModel,
    onNavigateToMatchDetails: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedFilter by remember { mutableStateOf(MatchFilter.ALL) }
    
    val categoryChips = listOf("All", "International", "League", "Domestic", "Women's", "More")
    var selectedCategory by remember { mutableStateOf(categoryChips[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(
                            text = "ScoreKnight",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Matches",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.loadMatches(isRefresh = true) }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                    IconButton(onClick = { /* Search */ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                    IconButton(onClick = { /* Calendar */ }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
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
            // Main Tabs
            TabRow(
                selectedTabIndex = selectedFilter.ordinal,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedFilter.ordinal]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                MatchFilter.values().forEach { filter ->
                    Tab(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        text = { 
                            Text(
                                text = filter.displayName, 
                                fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Normal
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

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (val state = uiState) {
                    is HomeUiState.Loading -> {
                        LoadingState()
                    }
                    is HomeUiState.Success -> {
                        // 1. Filter by Main Tab
                        var filteredMatches = state.matches.filter { match ->
                            val status = match.status.lowercase()
                            when (selectedFilter) {
                                MatchFilter.ALL -> true
                                MatchFilter.LIVE -> status.contains("live") || status.contains("inning") || status.contains("stump")
                                MatchFilter.UPCOMING -> status.contains("upcoming") || status.contains("yet to begin") || status.contains("toss")
                                MatchFilter.COMPLETED -> !status.contains("live") && !status.contains("inning") && !status.contains("upcoming") && !status.contains("yet to begin") && !status.contains("toss")
                            }
                        }
                        
                        // 2. Filter by Category Chip (Graceful mock filter)
                        if (selectedCategory != "All") {
                            filteredMatches = filteredMatches.filter { match ->
                                val allText = "${match.teamA} ${match.teamB} ${match.venue} ${match.status}".lowercase()
                                when (selectedCategory) {
                                    "Women's" -> allText.contains("women")
                                    "Domestic", "League" -> !allText.contains("international") && !allText.contains("test")
                                    "International" -> allText.contains("test") || allText.contains("odi") || allText.contains("t20i")
                                    else -> true // Fallback
                                }
                            }
                        }
                        
                        if (filteredMatches.isEmpty()) {
                            EmptyState(
                                title = "No Matches Found",
                                subtitle = "No matches match the selected filters."
                            )
                        } else {
                            // 3. Group by Date for Section Headers
                            val groupedMatches = filteredMatches.groupBy { 
                                if (it.matchDate.isNotBlank()) it.matchDate else "Other Matches"
                            }

                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                                    
                                    item {
                                        Spacer(modifier = Modifier.height(8.dp))
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
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
