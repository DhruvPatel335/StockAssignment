package com.upstox.interview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upstox.interview.network.RetrofitClient
import com.upstox.interview.repository.HoldingsRepositoryImpl
import com.upstox.interview.screens.userholdings.HoldingsScreen
import com.upstox.interview.viewmodel.HoldingsViewModel
import com.upstox.interview.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = HoldingsRepositoryImpl(RetrofitClient.instance)
        val viewModelFactory = ViewModelFactory(repository)

        setContent {
            val viewModel: HoldingsViewModel = viewModel(factory = viewModelFactory)
            HoldingsScreen(viewModel)
        }
    }
}
