package com.k9t.scoreknight.football.domain.model

data class FootballMatch(
    val id: String,
    val competition: String,
    val competitionLogo: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamLogo: String,
    val awayTeamLogo: String,
    val homeScore: String,
    val awayScore: String,
    val status: String,
    val elapsed: String,
    val venue: String,
    val matchDate: String,
    val type: String
)
