package com.k9t.scoreknight.football.data.repository

import com.k9t.scoreknight.football.data.mapper.toDomain
import com.k9t.scoreknight.football.data.remote.api.FootballApiService
import com.k9t.scoreknight.football.domain.model.*
import com.k9t.scoreknight.football.domain.repository.FootballRepository
import javax.inject.Inject

class FootballRepositoryImpl @Inject constructor(
    private val apiService: FootballApiService
) : FootballRepository {

    override suspend fun getHomeMatches(): Result<Map<String, List<FootballMatch>>> {
        return try {
            val response = apiService.getHomeMatches()
            val mapped = buildMap {
                put("live", response.liveMatches.map { it.toDomain("live") })
                put("upcoming", response.upcomingMatches.map { it.toDomain("upcoming") })
                put("completed", response.completedMatches.map { it.toDomain("completed") })
            }
            Result.success(mapped)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMatchDetails(id: String): Result<FootballMatchDetails> {
        return try {
            Result.success(apiService.getMatchDetails(id).toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStandings(): Result<List<FootballStanding>> {
        return try {
            val response = apiService.getStandings()
            val domainList = response.map { it.toDomain() }
            Result.success(domainList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
