package com.upstox.interview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upstox.interview.model.HoldingsUiState
import com.upstox.interview.repository.HoldingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch



class HoldingsViewModel(private val repository: HoldingsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HoldingsUiState>(HoldingsUiState.Loading)
    val uiState: StateFlow<HoldingsUiState> = _uiState.asStateFlow()

    init {
        fetchHoldings()
    }

    fun fetchHoldings() {
        viewModelScope.launch {
            _uiState.value = HoldingsUiState.Loading
            repository.getUserHoldings()
                .catch { e ->
                    _uiState.value = HoldingsUiState.Error(e.message ?: "An unknown error occurred")
                }
                .collect { holdings ->
                    _uiState.value = HoldingsUiState.Success(holdings)
                }
        }
    }
}
