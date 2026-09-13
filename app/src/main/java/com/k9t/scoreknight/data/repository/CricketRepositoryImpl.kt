package com.k9t.scoreknight.data.repository

import com.k9t.scoreknight.data.mapper.toDomain
import com.k9t.scoreknight.data.remote.api.CricketApiService
import com.k9t.scoreknight.domain.model.CricketMatch
import com.k9t.scoreknight.domain.repository.CricketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class CricketRepositoryImpl @Inject constructor(
    private val apiService: CricketApiService
) : CricketRepository {

    override suspend fun getHomeMatches(): Result<List<CricketMatch>> {
        return withContext(Dispatchers.IO) {
            try {
                val matchesDto = apiService.getHomeMatches()
                val matches = matchesDto.map { it.toDomain() }
                Result.success(matches)
            } catch (e: retrofit2.HttpException) {
                Result.failure(Exception("Network error: ${e.code()} - ${e.message()}"))
            } catch (e: IOException) {
                Result.failure(Exception("Connection failed: ${e.message}"))
            } catch (e: Exception) {
                Result.failure(Exception("Unexpected error: ${e.message}"))
            }
        }
    }
}
