package com.upstox.interview.screens.userholdings

import androidx.compose.foundation.background
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
import com.upstox.interview.utils.formatCurrency

@Composable
fun SummarySection(
    currentValue: Double,
    totalInvestment: Double,
    todaysPnl: Double,
    totalPnl: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        SummaryRow(label = "Current Value:", value = currentValue)
        SummaryRow(label = "Total Investment:", value = totalInvestment)
        SummaryRow(label = "Today's Profit & Loss:", value = todaysPnl)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total Profit & Loss:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                text = "₹ ${formatCurrency(totalPnl)}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun SummaryRow(label: String, value: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Medium)
        Text(text = "₹ ${formatCurrency(value)}")
    }
}
