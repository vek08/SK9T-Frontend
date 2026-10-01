package com.k9t.scoreknight.football.domain.model
data class FootballMatchDetails(
    val match: FootballMatch,
    val events: List<FootballEvent>
)
