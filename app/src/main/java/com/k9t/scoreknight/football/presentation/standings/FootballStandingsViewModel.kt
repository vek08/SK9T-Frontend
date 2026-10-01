package com.k9t.scoreknight.football.presentation.standings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k9t.scoreknight.football.domain.model.FootballStanding
import com.k9t.scoreknight.football.domain.usecase.GetFootballStandingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FootballStandingsUiState {
    data object Loading : FootballStandingsUiState
    data class Success(val standings: List<FootballStanding>) : FootballStandingsUiState
    data class Error(val message: String) : FootballStandingsUiState
}

@HiltViewModel
class FootballStandingsViewModel @Inject constructor(
    private val getStandingsUseCase: GetFootballStandingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FootballStandingsUiState>(FootballStandingsUiState.Loading)
    val uiState: StateFlow<FootballStandingsUiState> = _uiState.asStateFlow()

    init {
        fetchStandings()
    }

    fun fetchStandings() {
        viewModelScope.launch {
            _uiState.value = FootballStandingsUiState.Loading
            getStandingsUseCase().fold(
                onSuccess = { _uiState.value = FootballStandingsUiState.Success(it) },
                onFailure = { _uiState.value = FootballStandingsUiState.Error(it.message ?: "Error fetching standings") }
            )
        }
    }
}
