package com.k9t.scoreknight.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class FavoritesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    init {
        val savedFavorites = prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
        _favorites.value = savedFavorites
    }

    fun toggleFavorite(matchId: String) {
        val currentFavorites = _favorites.value.toMutableSet()
        if (currentFavorites.contains(matchId)) {
            currentFavorites.remove(matchId)
        } else {
            currentFavorites.add(matchId)
        }
        
        prefs.edit().putStringSet(KEY_FAVORITES, currentFavorites).apply()
        _favorites.value = currentFavorites
    }
    
    fun isFavorite(matchId: String): Boolean {
        return _favorites.value.contains(matchId)
    }

    companion object {
        private const val PREFS_NAME = "scoreknight_favorites"
        private const val KEY_FAVORITES = "favorite_match_ids"
    }
}
