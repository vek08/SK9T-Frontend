package com.k9t.scoreknight.football.data.remote.dto
import kotlinx.serialization.Serializable
@Serializable
data class FootballStandingDto(
    val position: String? = null,
    val teamName: String? = null,
    val teamLogo: String? = null,
    val played: String? = null,
    val won: String? = null,
    val drawn: String? = null,
    val lost: String? = null,
    val goalDifference: String? = null,
    val points: String? = null
)
