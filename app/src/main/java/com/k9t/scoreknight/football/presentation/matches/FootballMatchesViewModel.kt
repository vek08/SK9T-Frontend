package com.k9t.scoreknight.football.presentation.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k9t.scoreknight.football.domain.model.FootballMatch
import com.k9t.scoreknight.football.domain.repository.FootballRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FootballMatchesUiState {
    data object Loading : FootballMatchesUiState
    data class Success(val matches: List<FootballMatch>) : FootballMatchesUiState
    data class Error(val message: String) : FootballMatchesUiState
}

@HiltViewModel
class FootballMatchesViewModel @Inject constructor(
    private val repository: FootballRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FootballMatchesUiState>(FootballMatchesUiState.Loading)
    val uiState: StateFlow<FootballMatchesUiState> = _uiState.asStateFlow()

    private val _filter = MutableStateFlow("ALL")
    val filter: StateFlow<String> = _filter.asStateFlow()

    private var allMatches = emptyList<FootballMatch>()

    init {
        fetchMatches()
    }

    fun setFilter(newFilter: String) {
        _filter.value = newFilter
        applyFilter()
    }

    private fun applyFilter() {
        val f = _filter.value
        val filtered = if (f == "ALL") allMatches else allMatches.filter { it.type.equals(f, ignoreCase = true) }
        _uiState.value = FootballMatchesUiState.Success(filtered)
    }

    fun fetchMatches() {
        viewModelScope.launch {
            _uiState.value = FootballMatchesUiState.Loading
            repository.getHomeMatches().fold(
                onSuccess = { matchesMap ->
                    val live = matchesMap["live"] ?: emptyList()
                    val upcoming = matchesMap["upcoming"] ?: emptyList()
                    val completed = matchesMap["completed"] ?: emptyList()
                    allMatches = live + upcoming + completed
                    applyFilter()
                },
                onFailure = { error ->
                    _uiState.value = FootballMatchesUiState.Error(error.message ?: "An error occurred")
                }
            )
        }
    }
}
