package com.task.expencetracker.Feactures.budget



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.task.expencetracker.data.repo.BudgetRepository
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {
    private val budgetRepository = BudgetRepository(application)

    // LiveData for observing all budgets
    val budgets: LiveData<List<BudgetEntity>> = budgetRepository.getAllBudgets().asLiveData()

    /**
     * Inserts a new budget into the database.
     *
     * @param budget The budget entity to be inserted.
     */
    fun addBudget(budget: BudgetEntity) {
        viewModelScope.launch {
            budgetRepository.insertBudget(budget)
        }
    }

    /**
     * hejjo
     * Updates the spent amount of a budget for a specific category.
     *
     * @param category The category of the budget to be updated.
     * @param amount The amount to add to the spent value.
     */
    fun updateBudgetSpentAmount(category: String, amount: Double) {
        viewModelScope.launch {
            val budget = budgetRepository.getBudgetByCategory(category)
            budget?.let {
                it.spentAmount += amount
                budgetRepository.updateBudget(it)
            }
        }
    }

    /**
     * Deletes a budget from the database.
     *
     * @param budget The budget entity to be deleted.
     */
//    fun deleteBudget(budget: BudgetEntity) {
//        viewModelScope.launch {
//            budgetRepository.deleteBudget(budget)
//        }
//    }
}
