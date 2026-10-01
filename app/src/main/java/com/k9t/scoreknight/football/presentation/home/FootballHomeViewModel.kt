package com.k9t.scoreknight.football.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k9t.scoreknight.football.domain.usecase.GetFootballHomeMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.k9t.scoreknight.auth.data.TokenManager
import com.k9t.scoreknight.auth.presentation.AuthEventManager

@HiltViewModel
class FootballHomeViewModel @Inject constructor(
    private val getHomeMatchesUseCase: GetFootballHomeMatchesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FootballHomeUiState>(FootballHomeUiState.Loading)
    val uiState: StateFlow<FootballHomeUiState> = _uiState.asStateFlow()

    private val _favorites = MutableStateFlow<Set<String>>(emptySet())

    init {
        loadMatches()
    }

    fun loadMatches(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (!isRefresh) {
                _uiState.value = FootballHomeUiState.Loading
            }
            getHomeMatchesUseCase().fold(
                onSuccess = { matchesMap ->
                    val live = matchesMap["live"] ?: emptyList()
                    val upcoming = matchesMap["upcoming"] ?: emptyList()
                    val completed = matchesMap["completed"] ?: emptyList()

                    if (live.isEmpty() && upcoming.isEmpty() && completed.isEmpty()) {
                        _uiState.value = FootballHomeUiState.Empty
                    } else {
                        _uiState.value = FootballHomeUiState.Success(
                            liveMatches = live,
                            upcomingMatches = upcoming,
                            completedMatches = completed,
                            favorites = _favorites.value,
                            isRefreshing = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.value = FootballHomeUiState.Error(error.message ?: "An unknown error occurred")
                }
            )
        }
    }

    fun toggleFavorite(matchId: String) {
        val currentFavs = _favorites.value.toMutableSet()
        if (currentFavs.contains(matchId)) {
            currentFavs.remove(matchId)
        } else {
            currentFavs.add(matchId)
        }
        _favorites.value = currentFavs

        val currentState = _uiState.value
        if (currentState is FootballHomeUiState.Success) {
            _uiState.value = currentState.copy(favorites = currentFavs)
        }
    }
}
