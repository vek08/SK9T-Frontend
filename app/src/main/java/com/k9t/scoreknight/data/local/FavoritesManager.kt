package com.k9t.scoreknight.data.local

import com.k9t.scoreknight.auth.data.FavoritesApiService
import com.k9t.scoreknight.auth.data.TokenManager
import com.k9t.scoreknight.auth.domain.FavoriteDto
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Singleton
class FavoritesManager @Inject constructor(
    private val api: FavoritesApiService,
    private val tokenManager: TokenManager
) {
    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            tokenManager.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    fetchFavorites()
                } else {
                    _favorites.value = emptySet()
                }
            }
        }
    }

    private fun fetchFavorites() {
        scope.launch {
            try {
                val list = api.getFavorites()
                _favorites.value = list.map { it.matchId }.toSet()
            } catch (e: Exception) {
                // Ignore for now
            }
        }
    }

    fun toggleFavorite(matchId: String, sportType: String = "CRICKET") {
        val current = _favorites.value.toMutableSet()
        val isAdding = !current.contains(matchId)
        
        if (isAdding) {
            current.add(matchId)
        } else {
            current.remove(matchId)
        }
        _favorites.value = current
        
        scope.launch {
            try {
                if (isAdding) {
                    api.addFavorite(FavoriteDto(matchId, sportType))
                } else {
                    api.removeFavorite(matchId, sportType)
                }
            } catch (e: Exception) {
                // Revert on failure
                val reverted = _favorites.value.toMutableSet()
                if (isAdding) reverted.remove(matchId) else reverted.add(matchId)
                _favorites.value = reverted
            }
        }
    }

    fun isFavorite(matchId: String): Boolean {
        return _favorites.value.contains(matchId)
    }
}
