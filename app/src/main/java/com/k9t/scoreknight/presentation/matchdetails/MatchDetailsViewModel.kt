package com.k9t.scoreknight.presentation.matchdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k9t.scoreknight.data.local.FavoritesManager
import com.k9t.scoreknight.domain.repository.CricketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val repository: CricketRepository,
    private val favoritesManager: FavoritesManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val matchId: String = checkNotNull(savedStateHandle["matchId"])

    private val _isLoading = MutableStateFlow(true)
    private val _matchResult = MutableStateFlow<Result<com.k9t.scoreknight.domain.model.CricketMatch>?>(null)

    val uiState: StateFlow<MatchDetailsUiState> = combine(
        _isLoading,
        _matchResult,
        favoritesManager.favorites
    ) { isLoading, result, favorites ->
        if (isLoading && result == null) {
            return@combine MatchDetailsUiState.Loading
        }
        
        result?.fold(
            onSuccess = { match ->
                MatchDetailsUiState.Success(
                    match = match,
                    isFavorite = favorites.contains(match.matchId)
                )
            },
            onFailure = { exception ->
                MatchDetailsUiState.Error(exception.message ?: "Match not found")
            }
        ) ?: MatchDetailsUiState.Loading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MatchDetailsUiState.Loading
    )

    init {
        loadMatch()
    }

    fun loadMatch() {
        viewModelScope.launch {
            _isLoading.value = true
            _matchResult.value = repository.getMatchDetails(matchId)
            _isLoading.value = false
        }
    }

    fun toggleFavorite(matchId: String) {
        favoritesManager.toggleFavorite(matchId)
    }
}

