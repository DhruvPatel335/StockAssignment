package com.upstox.interview.repository

import com.upstox.interview.model.UserHolding
import com.upstox.interview.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HoldingsRepositoryImpl(private val apiService: ApiService) : HoldingsRepository {

    override fun getUserHoldings(): Flow<List<UserHolding>> = flow {
        val response = apiService.getHoldings()
        emit(response.data.userHolding)
    }.flowOn(Dispatchers.IO)
}
