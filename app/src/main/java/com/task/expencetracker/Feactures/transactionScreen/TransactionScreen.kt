package com.task.expencetracker.features.transactionScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.task.expencetracker.data.dataTransaction.PaymentTransaction
import com.task.expencetracker.data.repo.PaymentRepository
import com.task.expencetracker.database.ExpenseDatabase
import com.task.expencetracker.viewModel.PaymentViewModel
import com.task.expencetracker.viewModel.PaymentViewModelFactory

@Composable
fun PaymentListScreen() {
    val context = LocalContext.current
    val repository = PaymentRepository(ExpenseDatabase.getInstance(context).expenseDao()) // Fetch repository
    val factory = PaymentViewModelFactory(repository)
    val viewModel: PaymentViewModel = viewModel(factory = factory)

    // Observe the payments LiveData
    val paymentList by viewModel.payments.observeAsState(listOf())

    // Display the list of payments
    PaymentList(payments = paymentList)
}

@Composable
fun PaymentList(payments: List<PaymentTransaction>) {
    // Iterate over the list of payments and display each as a PaymentItem
    Column {
        payments.forEach { payment ->
            PaymentItem(payment = payment)
        }
    }
}

@Composable
fun PaymentItem(payment: PaymentTransaction) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = payment.title, style = MaterialTheme.typography.titleLarge)
            Text(text = "Amount: \$${payment.amount}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Date: ${payment.date}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Description: ${payment.description}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
