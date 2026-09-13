package com.k9t.scoreknight.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k9t.scoreknight.domain.usecase.GetHomeMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeMatchesUseCase: GetHomeMatchesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMatches()
    }

    fun loadMatches() {
        viewModelScope.launch {
            _uiState.update { HomeUiState.Loading }
            getHomeMatchesUseCase()
                .onSuccess { matches ->
                    if (matches.isEmpty()) {
                        _uiState.update { HomeUiState.Empty }
                    } else {
                        _uiState.update { HomeUiState.Success(matches) }
                    }
                }
                .onFailure { exception ->
                    _uiState.update { HomeUiState.Error(exception.message ?: "An unknown error occurred") }
                }
        }
    }
}
