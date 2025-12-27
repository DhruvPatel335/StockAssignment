package com.upstox.interview.screens.userholdings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.upstox.interview.model.UserHolding

@Composable
fun HoldingsContent(holdings: List<UserHolding>) {
    val totalCurrentValue = holdings.sumOf { it.ltp * it.quantity }
    val totalInvestment = holdings.sumOf { it.avgPrice * it.quantity }
    val totalPnl = totalCurrentValue - totalInvestment
    val todaysPnl = holdings.sumOf { (it.close - it.ltp) * it.quantity }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(holdings) { index, holding ->
                HoldingItem(holding)
                if (index < holdings.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = Color.LightGray
                    )
                }
            }
        }

        SummarySection(
            currentValue = totalCurrentValue,
            totalInvestment = totalInvestment,
            todaysPnl = todaysPnl,
            totalPnl = totalPnl
        )
    }
}
