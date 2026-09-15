package com.k9t.scoreknight.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HomeMatchesResponseDto(
    val liveMatches: List<CricketMatchDto> = emptyList(),
    val upcomingMatches: List<CricketMatchDto> = emptyList(),
    val completedMatches: List<CricketMatchDto> = emptyList()
)

