package com.k9t.scoreknight.football.data.remote.api

import com.k9t.scoreknight.football.data.remote.dto.HomeFootballMatchesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.k9t.scoreknight.football.data.remote.dto.FootballMatchDetailsDto
import com.k9t.scoreknight.football.data.remote.dto.FootballStandingDto

interface FootballApiService {
    @GET("api/football/home")
    suspend fun getHomeMatches(): HomeFootballMatchesResponseDto

    @GET("api/football/matches/{id}")
    suspend fun getMatchDetails(@Path("id") id: String): FootballMatchDetailsDto
    
    @GET("api/football/standings")
    suspend fun getStandings(@Query("league") league: String = "39", @Query("season") season: String = "2023"): List<FootballStandingDto>
}
