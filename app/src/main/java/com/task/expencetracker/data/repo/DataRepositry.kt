package com.task.expencetracker.data.repo

import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.model.ExpenceEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val dao: ExpenseDao) {


    // Fetch expenses between two dates
    suspend fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpenceEntity> {
        return dao.getExpensesBetweenDates(startDate, endDate)
    }

    // Fetch grouped expenses by month
    fun getExpensesGroupedByMonth(): Flow<List<ExpenceEntity>> {
        return dao.getExpensesGroupedByMonth()
    }

    // Insert a new expense into the database
    suspend fun insertExpense(expense: ExpenceEntity) {
        dao.insertExpense(expense)
    }

    // Fetch all expenses (if needed)
    fun getAllExpenses(): Flow<List<ExpenceEntity>> {
        return dao.getAllExpenses()
    }

    // In ExpenseRepository.kt
    suspend fun getExpensesByMonth(month: String): List<ExpenceEntity> {
        return dao.getExpensesByMonth(month) // Ensure this DAO method is correctly defined.
    }
}