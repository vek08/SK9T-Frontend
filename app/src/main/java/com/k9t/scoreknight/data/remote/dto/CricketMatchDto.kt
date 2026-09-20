package com.k9t.scoreknight.data.remote.dto

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable

data class CricketMatchDto(
    val matchId: String? = null,
    val teamA: String? = null,
    val teamB: String? = null,
    val teamALogo: String? = null,
    val teamBLogo: String? = null,
    val status: String? = null,
    val score: String? = null,
    val venue: String? = null,
    val matchDate: String? = null,
    val matchType: String? = null,
    val squads: List<MatchSquadDto>? = null
)
