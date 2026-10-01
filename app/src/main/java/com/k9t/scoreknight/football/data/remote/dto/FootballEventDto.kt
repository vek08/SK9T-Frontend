package com.k9t.scoreknight.football.data.remote.dto
import kotlinx.serialization.Serializable
@Serializable
data class FootballEventDto(
    val timeElapsed: String? = null,
    val teamName: String? = null,
    val teamLogo: String? = null,
    val playerName: String? = null,
    val assistName: String? = null,
    val type: String? = null,
    val detail: String? = null
)
