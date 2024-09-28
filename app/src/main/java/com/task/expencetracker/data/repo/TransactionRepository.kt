package com.task.expencetracker.data.repo

import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.dataTransaction.AddPaymentTransacton
import com.task.expencetracker.data.dataTransaction.Transaction
import com.task.expencetracker.data.model.ExpenceEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TransactionRepository(private val expenseDao:ExpenseDao) {

    // Method to get all payments
    fun getAllPayments(): Flow<List<AddPaymentTransacton>> {
        return expenseDao.getAllPayments()
    }

    // Function to get all transactions sorted by date
    suspend fun getSortedTransactions(): Flow<List<ExpenceEntity>> {
        return withContext(Dispatchers.IO) {
            expenseDao.getAllExpensesByDate()
        }
    }

    // Function to add a new transaction
    suspend fun addTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            expenseDao.insertliveTransaction(transaction)
        }
    }

}
