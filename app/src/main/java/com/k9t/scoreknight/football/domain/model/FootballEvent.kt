package com.k9t.scoreknight.football.domain.model
data class FootballEvent(
    val timeElapsed: String,
    val teamName: String,
    val teamLogo: String,
    val playerName: String,
    val assistName: String,
    val type: String,
    val detail: String
)
