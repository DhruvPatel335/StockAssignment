package com.upstox.interview.model

import com.google.gson.annotations.SerializedName

data class HoldingsResponse(
    @SerializedName("data")
    val data: HoldingsData
)
