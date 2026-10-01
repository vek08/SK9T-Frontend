package com.k9t.scoreknight.football.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k9t.scoreknight.football.domain.model.FootballMatch
import com.k9t.scoreknight.football.domain.usecase.GetFootballHomeMatchesUseCase
import com.k9t.scoreknight.data.local.FavoritesManager
import com.k9t.scoreknight.auth.data.TokenManager
import com.k9t.scoreknight.auth.presentation.AuthEventManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FootballHomeViewModel @Inject constructor(
    private val getHomeMatchesUseCase: GetFootballHomeMatchesUseCase,
    private val favoritesManager: FavoritesManager,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    private val _isRefreshing = MutableStateFlow(false)
    private val _matchesResult = MutableStateFlow<Result<Map<String, List<FootballMatch>>>?>(null)

    val uiState: StateFlow<FootballHomeUiState> = combine(
        _isLoading,
        _isRefreshing,
        _matchesResult,
        favoritesManager.favorites
    ) { isLoading, isRefreshing, result, favorites ->
        if (isLoading && result == null) {
            return@combine FootballHomeUiState.Loading
        }
        
        result?.fold(
            onSuccess = { matchesMap ->
                val live = matchesMap["live"] ?: emptyList()
                val upcoming = matchesMap["upcoming"] ?: emptyList()
                val completed = matchesMap["completed"] ?: emptyList()
                
                if (live.isEmpty() && upcoming.isEmpty() && completed.isEmpty()) {
                    FootballHomeUiState.Empty
                } else {
                    FootballHomeUiState.Success(
                        liveMatches = live,
                        upcomingMatches = upcoming,
                        completedMatches = completed,
                        favorites = favorites,
                        isRefreshing = isRefreshing
                    )
                }
            },
            onFailure = { error ->
                FootballHomeUiState.Error(error.message ?: "An unknown error occurred")
            }
        ) ?: FootballHomeUiState.Loading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FootballHomeUiState.Loading
    )

    init {
        loadMatches()
    }

    fun loadMatches(isRefresh: Boolean = false) {
        if (isRefresh) {
            _isRefreshing.value = true
        } else {
            _isLoading.value = true
        }
        
        viewModelScope.launch {
            _matchesResult.value = getHomeMatchesUseCase()
            _isLoading.value = false
            _isRefreshing.value = false
        }
    }

    fun toggleFavorite(matchId: String) {
        if (!tokenManager.isLoggedIn.value) {
            AuthEventManager.triggerAuthRequired()
        } else {
            // Note: Saving with sportType="FOOTBALL"
            favoritesManager.toggleFavorite(matchId, "FOOTBALL")
        }
    }
}
