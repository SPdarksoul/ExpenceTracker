package com.task.expencetracker.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.task.expencetracker.data.dataTransaction.PaymentTransaction
import com.task.expencetracker.data.repo.PaymentRepository
import kotlinx.coroutines.launch

class PaymentViewModel(private val repository: PaymentRepository) : ViewModel() {
    val payments: LiveData<List<PaymentTransaction>> = repository.getAllPayments().asLiveData()


    fun addPayment(payment: PaymentTransaction) {
        viewModelScope.launch {
            repository.insertPayment(payment)
        }
    }
}
class PaymentViewModelFactory(private val repository: PaymentRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return PaymentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}