package com.k9t.scoreknight.domain.repository

import com.k9t.scoreknight.domain.model.CricketMatch

interface CricketRepository {
    suspend fun getHomeMatches(): Result<Map<String, List<CricketMatch>>>
    suspend fun getMatchDetails(matchId: String): Result<CricketMatch>
}

