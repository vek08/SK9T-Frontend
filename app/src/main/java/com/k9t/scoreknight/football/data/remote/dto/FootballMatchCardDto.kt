package com.k9t.scoreknight.football.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FootballMatchCardDto(
    val id: Long? = null,
    val competition: String? = null,
    val competitionLogo: String? = null,
    val homeTeam: String? = null,
    val awayTeam: String? = null,
    val homeTeamLogo: String? = null,
    val awayTeamLogo: String? = null,
    val homeScore: String? = null,
    val awayScore: String? = null,
    val status: String? = null,
    val elapsed: String? = null,
    val venue: String? = null,
    val matchDate: String? = null
)
