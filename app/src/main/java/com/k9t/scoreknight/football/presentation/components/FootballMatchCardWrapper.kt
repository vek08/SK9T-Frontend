package com.k9t.scoreknight.football.presentation.components

import androidx.compose.runtime.Composable
import com.k9t.scoreknight.football.domain.model.FootballMatch

@Composable
fun FootballMatchCardWrapper(
    match: FootballMatch,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    onNavigateToMatchDetails: (String) -> Unit
) {
    FootballMatchCard(
        match = match,
        isFavorite = isFavorite,
        onFavoriteClick = onFavoriteClick,
        onClick = { onNavigateToMatchDetails(match.id) }
    )
}
