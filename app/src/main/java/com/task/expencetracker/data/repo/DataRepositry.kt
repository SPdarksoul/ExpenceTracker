package com.task.expencetracker.data.repo

import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.dataTransaction.PaymentTransaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {
    // Function to get all expenses from the DAO
    fun getAllExpenses(): Flow<List<ExpenceEntity>> {
        return expenseDao.getTopExpenses()
    }
}


class PaymentRepository(private val expenseDao: ExpenseDao) {

    fun getAllPayments() = expenseDao.getAllTransactions()

    suspend fun insertPayment(payment: PaymentTransaction) {
        expenseDao.insertTransaction(payment)
    }
}
