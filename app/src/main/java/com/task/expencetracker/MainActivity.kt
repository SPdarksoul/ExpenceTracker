package com.task.expencetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.task.expencetracker.accessibility.PaymentNotificationListener
import com.task.expencetracker.database.ExpenseDatabase
import com.task.expencetracker.ui.theme.ExpenceTrackerTheme
import com.task.expencetracker.viewModel.TransactionViewModel
import com.task.expencetracker.viewModel.TransactionViewModelFactory


import com.task.expensetracker.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var paymentNotificationListener: PaymentNotificationListener
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewModel
        val database = ExpenseDatabase.getInstance(this)
        val transactionViewModel = ViewModelProvider(this, TransactionViewModelFactory(database)).get(
            TransactionViewModel::class.java)


        setContent {
            ExpenceTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    setContent {
    MainContent()
                    }
                }
            }
        }
    }}