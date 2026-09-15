package com.k9t.scoreknight.presentation.matchdetails

import com.k9t.scoreknight.domain.model.CricketMatch

sealed class MatchDetailsUiState {
    object Loading : MatchDetailsUiState()
    data class Success(
        val match: CricketMatch,
        val isFavorite: Boolean
    ) : MatchDetailsUiState()
    data class Error(val message: String) : MatchDetailsUiState()
}

