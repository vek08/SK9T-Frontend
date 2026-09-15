package com.k9t.scoreknight.data.remote.api

import com.k9t.scoreknight.data.remote.dto.CricketMatchDto
import com.k9t.scoreknight.data.remote.dto.HomeMatchesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CricketApiService {
    @GET("api/cricket/home")
    suspend fun getHomeMatches(): HomeMatchesResponseDto

    @GET("api/cricket/matches/{matchId}")
    suspend fun getMatchDetails(@Path("matchId") matchId: String): CricketMatchDto
}

