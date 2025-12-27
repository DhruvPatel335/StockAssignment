package com.upstox.interview.repository

import com.upstox.interview.model.UserHolding
import kotlinx.coroutines.flow.Flow

interface HoldingsRepository {
    fun getUserHoldings(): Flow<List<UserHolding>>
}
