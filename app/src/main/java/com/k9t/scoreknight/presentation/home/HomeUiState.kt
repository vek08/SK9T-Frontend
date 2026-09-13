package com.k9t.scoreknight.presentation.home

import com.k9t.scoreknight.domain.model.CricketMatch

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val matches: List<CricketMatch>,
        val favorites: Set<String> = emptySet(),
        val isRefreshing: Boolean = false
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
    data object Empty : HomeUiState
}
