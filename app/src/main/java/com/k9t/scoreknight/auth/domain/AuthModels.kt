package com.k9t.scoreknight.auth.domain
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(val email: String, val password: String = "")

@Serializable
data class RegisterRequest(val name: String, val email: String, val password: String = "")

@Serializable
data class GoogleAuthRequest(val idToken: String)

@Serializable
data class AuthResponse(val token: String, val user: UserDto)

@Serializable
data class UserDto(val id: Long, val name: String, val email: String, val profileImage: String? = null, val authProvider: String)

@Serializable
data class FavoriteDto(val matchId: String, val sportType: String)

