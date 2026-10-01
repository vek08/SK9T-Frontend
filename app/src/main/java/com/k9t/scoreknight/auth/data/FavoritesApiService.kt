package com.k9t.scoreknight.auth.data

import com.k9t.scoreknight.auth.domain.FavoriteDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FavoritesApiService {
    @GET("/api/favorites")
    suspend fun getFavorites(): List<FavoriteDto>

    @POST("/api/favorites")
    suspend fun addFavorite(@Body request: FavoriteDto)

    @DELETE("/api/favorites")
    suspend fun removeFavorite(@Query("matchId") matchId: String, @Query("sportType") sportType: String)
}
