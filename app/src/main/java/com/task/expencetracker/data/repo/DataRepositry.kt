package com.task.expencetracker.data.repo


import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.dataTransaction.PaymentTransaction
import com.task.expencetracker.data.dataTransaction.Transaction
import com.task.expencetracker.data.model.ExpenceEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val dao: ExpenseDao) {



    suspend fun getExpensesByMonth(month: String): List<ExpenceEntity> {
        return dao.getExpensesByMonth(month)
    }

    suspend fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpenceEntity> {
        return dao.getExpensesBetweenDates(startDate, endDate)
    }

    fun getExpensesGroupedByMonth(): Flow<List<ExpenceEntity>> {
        return dao.getExpensesGroupedByMonth()
    }
}
class PaymentRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {

    fun getAllPayments(): Flow<List<Transaction>> {
        return expenseDao.getAllTransactions() // Ensure this returns a Flow
    }

    suspend fun insertPayment(payment: PaymentTransaction) {
        expenseDao.insertTransaction(payment)
    }
}
