package com.k9t.scoreknight.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k9t.scoreknight.data.local.FavoritesManager
import com.k9t.scoreknight.domain.model.CricketMatch
import com.k9t.scoreknight.domain.usecase.GetHomeMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeMatchesUseCase: GetHomeMatchesUseCase,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    private val _isRefreshing = MutableStateFlow(false)
    private val _matchesResult = MutableStateFlow<Result<Map<String, List<CricketMatch>>>?>(null)

    val uiState: StateFlow<HomeUiState> = combine(
        _isLoading,
        _isRefreshing,
        _matchesResult,
        favoritesManager.favorites
    ) { isLoading, isRefreshing, result, favorites ->
        if (isLoading && result == null) {
            return@combine HomeUiState.Loading
        }
        
        result?.fold(
            onSuccess = { matchesMap ->
                val live = matchesMap["live"] ?: emptyList()
                val upcoming = matchesMap["upcoming"] ?: emptyList()
                val completed = matchesMap["completed"] ?: emptyList()
                val allMatches = live + upcoming + completed
                
                if (allMatches.isEmpty()) {
                    HomeUiState.Empty
                } else {
                    HomeUiState.Success(
                        matches = allMatches,
                        liveMatches = live,
                        upcomingMatches = upcoming,
                        completedMatches = completed,
                        favorites = favorites,
                        isRefreshing = isRefreshing
                    )
                }
            },
            onFailure = { exception ->
                HomeUiState.Error(exception.message ?: "An unknown error occurred")
            }
        ) ?: HomeUiState.Loading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState.Loading
    )

    init {
        loadMatches()
    }

    fun loadMatches(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _isRefreshing.value = true
            } else {
                _isLoading.value = true
            }
            
            val result = getHomeMatchesUseCase()
            _matchesResult.value = result
            
            _isLoading.value = false
            _isRefreshing.value = false
        }
    }

    fun toggleFavorite(matchId: String) {
        favoritesManager.toggleFavorite(matchId)
    }
}

