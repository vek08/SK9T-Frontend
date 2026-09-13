package com.k9t.scoreknight.data.mapper

import com.k9t.scoreknight.data.remote.dto.CricketMatchDto
import com.k9t.scoreknight.domain.model.CricketMatch

fun CricketMatchDto.toDomain(): CricketMatch {
    return CricketMatch(
        matchId = matchId ?: "",
        teamA = teamA ?: "Unknown Team",
        teamB = teamB ?: "Unknown Team",
        teamALogo = teamALogo,
        teamBLogo = teamBLogo,
        status = status ?: "Status Unavailable",
        score = score,
        venue = venue ?: "Venue Unavailable",
        matchDate = matchDate ?: ""
    )
}
