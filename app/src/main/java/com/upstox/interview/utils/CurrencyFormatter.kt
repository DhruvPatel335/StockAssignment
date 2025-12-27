package com.upstox.interview.utils

import java.util.Locale

fun formatCurrency(value: Double): String {
    return String.format(Locale.getDefault(), "%,.2f", value)
}
