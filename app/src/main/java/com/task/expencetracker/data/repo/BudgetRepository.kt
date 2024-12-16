package com.task.expencetracker.data.repo


import android.content.Context
import com.task.expencetracker.Feactures.budget.BudgetEntity
import com.task.expencetracker.data.dao.ExpenseDao

import com.task.expencetracker.database.ExpenseDatabase
import kotlinx.coroutines.flow.Flow

class BudgetRepository(context: Context) {

    private val budgetDao: ExpenseDao = ExpenseDatabase.getInstance(context).expenseDao()

    // Fetch all budgets from the database
    fun getAllBudgets(): Flow<List<BudgetEntity>> {
        return budgetDao.getAllBudgets()
    }

    // Insert a new budget entry into the database
    suspend fun insertBudget(budget: BudgetEntity) {
        budgetDao.insertBudget(budget)
    }

    // Update an existing budget entry in the database
    suspend fun updateBudget(budget: BudgetEntity) {
        budgetDao.updateBudget(budget)
    }

    // Get a budget entry by its category
    suspend fun getBudgetByCategory(category: String): BudgetEntity? {
        return budgetDao.getBudgetByCategory(category)
    }
}
