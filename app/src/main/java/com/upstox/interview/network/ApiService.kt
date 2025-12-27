package com.upstox.interview.network

import com.upstox.interview.model.HoldingsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getHoldings(): HoldingsResponse
}
