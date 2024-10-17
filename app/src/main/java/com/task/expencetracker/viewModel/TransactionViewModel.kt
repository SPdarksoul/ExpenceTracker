package com.task.expencetracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.task.expencetracker.data.dataTransaction.Transaction
import com.task.expencetracker.database.ExpenseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// ViewModel to manage transactions
class TransactionViewModel(private val database: ExpenseDatabase) : ViewModel() {

    // Observes all transactions in the database using Flow
    val transactions: Flow<List<Transaction>> = database.expenseDao().getAllTransactions()

    // Adds a new transaction to the database
    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            database.expenseDao().insertliveTransaction(transaction)
        }
    }
}

// Factory to create TransactionViewModel with a database parameter
class TransactionViewModelFactory(private val database: ExpenseDatabase) : ViewModelProvider.Factory {

    // Create a new instance of the ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
