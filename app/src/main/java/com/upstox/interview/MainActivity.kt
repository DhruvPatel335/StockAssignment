package com.upstox.interview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upstox.interview.model.HoldingsUiState
import com.upstox.interview.model.UserHolding
import com.upstox.interview.network.RetrofitClient
import com.upstox.interview.repository.HoldingsRepositoryImpl
import com.upstox.interview.viewmodel.HoldingsViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = HoldingsRepositoryImpl(RetrofitClient.instance)
        val viewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HoldingsViewModel(repository) as T
            }
        }

        setContent {
            val viewModel: HoldingsViewModel = viewModel(factory = viewModelFactory)
            HoldingsScreen(viewModel)
        }
    }
}

@Composable
fun HoldingsScreen(viewModel: HoldingsViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF6200EE))
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "Upstox Holding",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            when (val state = uiState) {
                is HoldingsUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is HoldingsUiState.Success -> {
                    HoldingsContent(state.holdings)
                }
                is HoldingsUiState.Error -> {
                    Text(
                        text = state.message,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }
            }
        }
    }
}

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
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
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

fun formatCurrency(value: Double): String {
    return String.format(Locale.getDefault(), "%,.2f", value)
}
