package com.k9t.scoreknight.domain.model

data class CricketBanner(
    val id: String,
    val backgroundUrl: String?,
    val playerImageUrl: String?,
    val playerName: String,
    val tagline: String,
    val destination: String?
)
