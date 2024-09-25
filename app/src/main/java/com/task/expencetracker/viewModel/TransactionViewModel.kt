package com.task.expencetracker.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.dataTransaction.AddPaymentTransacton
import com.task.expencetracker.data.dataTransaction.PaymentTransaction
import com.task.expencetracker.data.repo.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TransactionListViewModel @Inject constructor(private val dao: ExpenseDao) : ViewModel() {

    val transactions: StateFlow<List<PaymentTransaction>> = dao.getAllTransactions().stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    fun insertTransaction(transaction: PaymentTransaction) {
        viewModelScope.launch {
            try {
                dao.insertTransaction(transaction)
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Error inserting transaction: ${e.message}", e)
            }
        }
    }
}
