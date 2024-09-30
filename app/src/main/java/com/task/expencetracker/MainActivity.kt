package com.task.expencetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.task.expencetracker.accessibility.PaymentNotificationListener
import com.task.expencetracker.database.ExpenseDatabase
import com.task.expencetracker.navigation.Navigation
import com.task.expencetracker.ui.theme.ExpenceTrackerTheme
import com.task.expencetracker.viewModel.TransactionViewModel
import com.task.expencetracker.viewModel.TransactionViewModelFactory
import com.task.expensetracker.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var paymentNotificationListener: PaymentNotificationListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewModel
        val database = ExpenseDatabase.getInstance(this)
        val transactionViewModel = ViewModelProvider(this, TransactionViewModelFactory(database)).get(
            TransactionViewModel::class.java)


        paymentNotificationListener = PaymentNotificationListener()
        paymentNotificationListener.setViewModel(transactionViewModel)
        setContent {
            ExpenceTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    setContent {
                 MainScreen()
                }
            }
        }
    }
}}
