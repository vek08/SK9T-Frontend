package com.k9t.scoreknight.football.domain.repository

import com.k9t.scoreknight.football.domain.model.*

interface FootballRepository {
    suspend fun getHomeMatches(): Result<Map<String, List<FootballMatch>>>

    suspend fun getMatchDetails(id: String): Result<FootballMatchDetails>
    suspend fun getStandings(): Result<List<FootballStanding>>
}
