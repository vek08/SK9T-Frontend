package com.k9t.scoreknight.domain.model

data class MatchSquad(
    val teamName: String,
    val img: String?,
    val players: List<MatchPlayer>
)

data class MatchPlayer(
    val id: String,
    val name: String,
    val role: String,
    val battingStyle: String,
    val bowlingStyle: String,
    val country: String,
    val playerImg: String?
)

