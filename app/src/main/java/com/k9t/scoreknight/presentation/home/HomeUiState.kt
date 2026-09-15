package com.k9t.scoreknight.presentation.home

import com.k9t.scoreknight.domain.model.CricketMatch

sealed class HomeUiState {
    object Loading : HomeUiState()
    
    data class Success(
        val matches: List<CricketMatch>,
        val liveMatches: List<CricketMatch>,
        val upcomingMatches: List<CricketMatch>,
        val completedMatches: List<CricketMatch>,
        val favorites: Set<String>,
        val isRefreshing: Boolean = false
    ) : HomeUiState()
    
    data class Error(val message: String) : HomeUiState()
    
    object Empty : HomeUiState()
}

