package com.k9t.scoreknight.presentation.home

import com.k9t.scoreknight.domain.model.CricketMatch

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val matches: List<CricketMatch>) : HomeUiState
    data class Error(val message: String) : HomeUiState
    data object Empty : HomeUiState
}
