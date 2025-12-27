package com.upstox.interview.model

sealed class HoldingsUiState {
    object Loading : HoldingsUiState()
    data class Success(val holdings: List<UserHolding>) : HoldingsUiState()
    data class Error(val message: String) : HoldingsUiState()
}
