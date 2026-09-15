package com.k9t.scoreknight.presentation.matchdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.k9t.scoreknight.domain.model.CricketMatch
import com.k9t.scoreknight.presentation.components.EmptyState
import com.k9t.scoreknight.presentation.components.ErrorState
import com.k9t.scoreknight.presentation.components.LoadingState

enum class MatchTab(val title: String) {
    INFO("INFO"),
    LIVE("LIVE"),
    SCORECARD("SCORECARD"),
    SQUADS("SQUADS"),
    OVERS("OVERS"),
    HIGHLIGHTS("HIGHLIGHTS")
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MatchDetailsScreen(
    viewModel: MatchDetailsViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { MatchTab.values().size })
    val coroutineScope = rememberCoroutineScope()

    when (val state = uiState) {
        is MatchDetailsUiState.Loading -> {
            LoadingState()
        }
        is MatchDetailsUiState.Success -> {
            val match = state.match
            val isFavorite = state.isFavorite

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "${match.teamA} v ${match.teamB}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { viewModel.toggleFavorite(match.matchId) }) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            IconButton(onClick = { /* More */ }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "More options",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
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
                    // Premium Tab Bar
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        edgePadding = 0.dp,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                height = 3.dp
                            )
                        },
                        divider = {}
                    ) {
                        MatchTab.values().forEachIndexed { index, tab ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = { 
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = {
                                    Text(
                                        text = tab.title,
                                        fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 13.sp
                                    )
                                }
                            )
                        }
                    }

                    // Match Header always visible above the pager
                    MatchDetailsHeader(match = match)

                    // Content Below Tabs using HorizontalPager
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        val tab = MatchTab.values()[page]
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                                when (tab) {
                                    MatchTab.INFO -> MatchInfoTab(match = match)
                                    MatchTab.LIVE -> MatchLiveTab(match = match)
                                    MatchTab.SCORECARD -> UnavailableTabState("Scorecard not available", "Detailed scorecard data is not provided by the current API.")
                                    MatchTab.SQUADS -> MatchSquadsTab(match = match)
                                    MatchTab.OVERS -> UnavailableTabState("Overs data not available", "Ball-by-ball data is not provided by the current API.")
                                    MatchTab.HIGHLIGHTS -> UnavailableTabState("Highlights not available", "Match highlights are not provided by the current API.")
                                }
                            }
                        }
                    }
                }
            }
        }
        is MatchDetailsUiState.Error -> {
            ErrorState(
                message = state.message,
                onRetry = { viewModel.loadMatch() }
            )
        }
    }
}

@Composable
private fun MatchDetailsHeader(match: CricketMatch) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Scores Section
            if (match.parsedScores != null && match.parsedScores.isNotEmpty()) {
                val firstScore = match.parsedScores.getOrNull(0)
                val secondScore = match.parsedScores.getOrNull(1)

                TeamScoreRowDetailed(
                    teamName = match.teamA,
                    logoUrl = match.teamALogo,
                    scoreText = if (firstScore?.inningName?.contains(match.teamA, ignoreCase = true) == true) {
                        firstScore.scoreDetails
                    } else if (secondScore?.inningName?.contains(match.teamA, ignoreCase = true) == true) {
                        secondScore.scoreDetails
                    } else {
                        firstScore?.scoreDetails ?: ""
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                TeamScoreRowDetailed(
                    teamName = match.teamB,
                    logoUrl = match.teamBLogo,
                    scoreText = if (secondScore?.inningName?.contains(match.teamB, ignoreCase = true) == true) {
                        secondScore.scoreDetails
                    } else if (firstScore?.inningName?.contains(match.teamB, ignoreCase = true) == true) {
                        firstScore.scoreDetails
                    } else {
                        secondScore?.scoreDetails ?: ""
                    }
                )
            } else {
                TeamScoreRowDetailed(teamName = match.teamA, logoUrl = match.teamALogo, scoreText = "")
                Spacer(modifier = Modifier.height(12.dp))
                TeamScoreRowDetailed(teamName = match.teamB, logoUrl = match.teamBLogo, scoreText = "")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Result/Status
            Text(
                text = match.status,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = if (match.status.lowercase().contains("live")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun TeamScoreRowDetailed(teamName: String, logoUrl: String?, scoreText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AsyncImage(
                    model = logoUrl,
                    contentDescription = "$teamName Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = teamName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (scoreText.isNotBlank()) {
            Text(
                text = scoreText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun MatchInfoTab(match: CricketMatch) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        InfoSectionTitle("MATCH INFO")
        
        InfoRow(label = "Match", value = "${match.teamA} vs ${match.teamB}")
        InfoRow(label = "Date", value = match.matchDate.ifBlank { "Date Unavailable" })
        InfoRow(label = "Venue", value = match.venue.ifBlank { "Venue Unavailable" })
        InfoRow(label = "Status", value = match.status)

        Spacer(modifier = Modifier.height(24.dp))
        
        InfoSectionTitle("VENUE GUIDE")
        InfoRow(label = "Stadium", value = match.venue.ifBlank { "N/A" })
        InfoRow(label = "City", value = "N/A")
    }
}

@Composable
private fun MatchLiveTab(match: CricketMatch) {
    val isLive = match.status.lowercase().contains("live") || match.status.lowercase().contains("inning")
    
    if (isLive) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            InfoSectionTitle("LIVE UPDATE")
            InfoRow(label = "Current Status", value = match.status)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (match.parsedScores != null && match.parsedScores.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        match.parsedScores.forEach { score ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(score.inningName, fontWeight = FontWeight.SemiBold)
                                Text(score.scoreDetails, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    } else {
        UnavailableTabState("Match is not live", "Live updates are only available during active play.")
    }
}

@Composable
private fun InfoSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.35f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.65f)
        )
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
}

@Composable
private fun UnavailableTabState(title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MatchSquadsTab(match: CricketMatch) {
    if (match.squads.isNullOrEmpty()) {
        UnavailableTabState("Squads not available", "Player squads are not provided by the current API or are not announced yet.")
        return
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        match.squads.forEach { squad ->
            InfoSectionTitle(squad.teamName)
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    squad.players.forEachIndexed { index, player ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surface)
                            ) {
                                if (player.playerImg != null) {
                                    AsyncImage(
                                        model = player.playerImg,
                                        contentDescription = "${player.name} image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = player.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                if (player.role.isNotBlank()) {
                                    Text(
                                        text = player.role,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                        if (index < squad.players.size - 1) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                        }
                    }
                }
            }
        }
    }
}

