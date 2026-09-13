package com.k9t.scoreknight.domain.repository

import com.k9t.scoreknight.domain.model.CricketMatch

interface CricketRepository {
    suspend fun getHomeMatches(): Result<List<CricketMatch>>
}
