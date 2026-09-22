package com.k9t.scoreknight.data.repository

import com.k9t.scoreknight.data.mapper.toDomain
import com.k9t.scoreknight.data.remote.api.CricketApiService
import com.k9t.scoreknight.domain.model.CricketMatch
import com.k9t.scoreknight.domain.repository.CricketRepository
import javax.inject.Inject

class CricketRepositoryImpl @Inject constructor(
    private val api: CricketApiService
) : CricketRepository {

    override suspend fun getHomeMatches(): Result<Map<String, List<CricketMatch>>> {
        return try {
            val response = api.getHomeMatches()
            val result = mapOf(
                "live" to response.liveMatches.map { it.toDomain("LIVE") },
                "upcoming" to response.upcomingMatches.map { it.toDomain("UPCOMING") },
                "completed" to response.completedMatches.map { it.toDomain("FINISHED") }
            )
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMatchDetails(matchId: String): Result<CricketMatch> {
        return try {
            val response = api.getMatchDetails(matchId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

