package com.k9t.scoreknight.auth.data

import com.k9t.scoreknight.auth.domain.*
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApiService,
    private val tokenManager: TokenManager
) {
    val isLoggedIn: StateFlow<Boolean> = tokenManager.isLoggedIn
    val currentUser: StateFlow<UserDto?> = tokenManager.currentUser
    
    suspend fun login(request: AuthRequest) {
        val response = api.login(request)
        tokenManager.saveAuth(response.token, response.user)
    }

    suspend fun register(request: RegisterRequest) {
        val response = api.register(request)
        tokenManager.saveAuth(response.token, response.user)
    }

    suspend fun googleLogin(idToken: String) {
        val response = api.googleLogin(GoogleAuthRequest(idToken))
        tokenManager.saveAuth(response.token, response.user)
    }

    fun logout() {
        tokenManager.clearAuth()
    }
}
