package com.task.expencetracker.data.repo

import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.dataTransaction.AddPaymentTransacton
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val expenseDao:ExpenseDao) {

    // Method to get all payments
    fun getAllPayments(): Flow<List<AddPaymentTransacton>> {
        return expenseDao.getAllPayments()
    }


}
