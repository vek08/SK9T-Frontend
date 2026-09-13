package com.k9t.scoreknight.domain.model

data class CricketMatch(
    val matchId: String,
    val teamA: String,
    val teamB: String,
    val teamALogo: String?,
    val teamBLogo: String?,
    val status: String,
    val score: String?,
    val venue: String,
    val matchDate: String
)
