package com.k9t.scoreknight.auth.data;

import com.k9t.scoreknight.auth.domain.AuthRequest;
import com.k9t.scoreknight.auth.domain.AuthResponse;
import com.k9t.scoreknight.auth.domain.GoogleAuthRequest;
import com.k9t.scoreknight.auth.domain.RegisterRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;

interface AuthApiService {
    @POST("/api/auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
    
    @POST("/api/auth/google")
    suspend fun googleLogin(@Body request: GoogleAuthRequest): AuthResponse
}

