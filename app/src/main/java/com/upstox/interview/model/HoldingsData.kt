package com.upstox.interview.model

import com.google.gson.annotations.SerializedName

data class HoldingsData(
    @SerializedName("userHolding")
    val userHolding: List<UserHolding>
)
