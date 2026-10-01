package com.k9t.scoreknight.football.presentation.home

import com.k9t.scoreknight.football.domain.model.FootballMatch

sealed interface FootballHomeUiState {
    data object Loading : FootballHomeUiState
    data class Success(
        val liveMatches: List<FootballMatch>,
        val upcomingMatches: List<FootballMatch>,
        val completedMatches: List<FootballMatch>,
        val favorites: Set<String> = emptySet(),
        val isRefreshing: Boolean = false
    ) : FootballHomeUiState {
        val matches: List<FootballMatch>
            get() = liveMatches + upcomingMatches + completedMatches
    }
    data class Error(val message: String) : FootballHomeUiState
    data object Empty : FootballHomeUiState
}
