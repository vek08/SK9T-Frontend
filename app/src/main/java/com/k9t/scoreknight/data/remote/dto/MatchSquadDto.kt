package com.k9t.scoreknight.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MatchSquadDto(
    val teamName: String? = null,
    val img: String? = null,
    val players: List<MatchPlayerDto>? = null
)

@Serializable
data class MatchPlayerDto(
    val id: String? = null,
    val name: String? = null,
    val role: String? = null,
    val battingStyle: String? = null,
    val bowlingStyle: String? = null,
    val country: String? = null,
    val playerImg: String? = null
)

