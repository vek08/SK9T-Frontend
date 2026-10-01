package com.k9t.scoreknight.auth.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import com.k9t.scoreknight.auth.domain.UserDto

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _isLoggedIn = MutableStateFlow(getToken() != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    private val _currentUser = MutableStateFlow<UserDto?>(getUser())
    val currentUser: StateFlow<UserDto?> = _currentUser.asStateFlow()

    fun saveAuth(token: String, user: UserDto) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
        prefs.edit().putString(KEY_USER, Json.encodeToString(user)).apply()
        _isLoggedIn.value = true
        _currentUser.value = user
    }

    fun clearAuth() {
        prefs.edit().remove(KEY_TOKEN).apply()
        prefs.edit().remove(KEY_USER).apply()
        _isLoggedIn.value = false
        _currentUser.value = null
    }

    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }
    
    private fun getUser(): UserDto? {
        val userJson = prefs.getString(KEY_USER, null) ?: return null
        return try {
            Json.decodeFromString<UserDto>(userJson)
        } catch(e: Exception) {
            null
        }
    }

    companion object {
        private const val PREFS_NAME = "scoreknight_auth"
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_USER = "current_user"
    }
}
