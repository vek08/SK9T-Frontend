package com.k9t.scoreknight.football.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k9t.scoreknight.football.domain.model.FootballMatchDetails
import com.k9t.scoreknight.football.domain.usecase.GetFootballMatchDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FootballMatchDetailsUiState {
    data object Loading : FootballMatchDetailsUiState
    data class Success(val details: FootballMatchDetails) : FootballMatchDetailsUiState
    data class Error(val message: String) : FootballMatchDetailsUiState
}

@HiltViewModel
class FootballMatchDetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetFootballMatchDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val fixtureId: String = savedStateHandle.get<String>("matchId") ?: ""

    private val _uiState = MutableStateFlow<FootballMatchDetailsUiState>(FootballMatchDetailsUiState.Loading)
    val uiState: StateFlow<FootballMatchDetailsUiState> = _uiState.asStateFlow()

    init {
        if (fixtureId.isNotEmpty()) {
            fetchDetails()
        } else {
            _uiState.value = FootballMatchDetailsUiState.Error("Invalid Match ID")
        }
    }

    fun fetchDetails() {
        viewModelScope.launch {
            _uiState.value = FootballMatchDetailsUiState.Loading
            getDetailsUseCase(fixtureId).fold(
                onSuccess = { _uiState.value = FootballMatchDetailsUiState.Success(it) },
                onFailure = { _uiState.value = FootballMatchDetailsUiState.Error(it.message ?: "Error fetching details") }
            )
        }
    }
}
