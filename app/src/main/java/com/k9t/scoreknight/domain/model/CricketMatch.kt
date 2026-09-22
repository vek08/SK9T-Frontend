package com.k9t.scoreknight.domain.model

data class InningScore(
    val inningName: String,
    val scoreDetails: String
)

data class CricketMatch(
    val matchId: String,
    val matchState: String,
    val teamA: String,
    val teamB: String,
    val teamALogo: String?,
    val teamBLogo: String?,
    val status: String,
    val score: String?,
    val parsedScores: List<InningScore>?,
    val venue: String,
    val matchDate: String,
    val matchType: String? = null,
    val squads: List<MatchSquad>? = null
)
