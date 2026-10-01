package com.k9t.scoreknight.football.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HomeFootballMatchesResponseDto(
    val liveMatches: List<FootballMatchCardDto> = emptyList(),
    val upcomingMatches: List<FootballMatchCardDto> = emptyList(),
    val completedMatches: List<FootballMatchCardDto> = emptyList()
)
