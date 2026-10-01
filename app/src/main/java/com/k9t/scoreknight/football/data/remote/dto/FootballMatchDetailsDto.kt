package com.k9t.scoreknight.football.data.remote.dto
import kotlinx.serialization.Serializable
@Serializable
data class FootballMatchDetailsDto(
    val match: FootballMatchCardDto? = null,
    val events: List<FootballEventDto>? = emptyList()
)
