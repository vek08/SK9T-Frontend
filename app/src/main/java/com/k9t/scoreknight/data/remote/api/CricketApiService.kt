package com.k9t.scoreknight.data.remote.api

import com.k9t.scoreknight.data.remote.dto.CricketMatchDto
import retrofit2.http.GET

interface CricketApiService {
    @GET("api/cricket/home")
    suspend fun getHomeMatches(): List<CricketMatchDto>
}
