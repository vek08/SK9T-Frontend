package com.k9t.scoreknight.football.domain.model
data class FootballStanding(
    val position: String,
    val teamName: String,
    val teamLogo: String,
    val played: String,
    val won: String,
    val drawn: String,
    val lost: String,
    val goalDifference: String,
    val points: String
)
