package com.upstox.interview.screens.userholdings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upstox.interview.model.UserHolding
import com.upstox.interview.utils.formatCurrency

@Composable
fun HoldingItem(holding: UserHolding) {
    val pnl = (holding.ltp - holding.avgPrice) * holding.quantity

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = holding.symbol, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "LTP: ₹ ${formatCurrency(holding.ltp)}")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Qty: ${holding.quantity}", color = Color.Gray)
            Text(
                text = "P&L: ₹ ${formatCurrency(pnl)}",
                fontWeight = FontWeight.Medium
            )
        }
    }
}
