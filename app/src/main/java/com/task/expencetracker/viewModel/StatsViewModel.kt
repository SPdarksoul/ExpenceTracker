package com.task.expencetracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.data.repo.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
@HiltViewModel
class StatsViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _entries = MutableStateFlow<List<ExpenceEntity>>(emptyList())
    val entries: StateFlow<List<ExpenceEntity>> = _entries.asStateFlow()

    /**
     * Loads the expenses for the given month and filters them by the month.
     */
    fun loadExpensesForMonth(month: String) {
        viewModelScope.launch {
            try {
                // Retrieve expenses for the specified month
                val expenses = expenseRepository.getExpensesByMonth(month)

                // Filter expenses by month
                _entries.value = expenses.filter { expense ->
                    try {
                        val expenseDate = LocalDate.parse(expense.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        expenseDate.format(DateTimeFormatter.ofPattern("yyyy-MM")) == month // Compare formatted date
                    } catch (e: Exception) {
                        false // Skip entries with invalid date formats
                    }
                }
            } catch (e: Exception) {
                // Handle any errors in data retrieval
                _entries.value = emptyList() // Reset entries on error
            }
        }
    }

    /**
     * Returns a list of chart entries based on the expenses.
     */
    fun getEntriesForChart(entries: List<ExpenceEntity>): List<Entry> {
        return entries.mapIndexed { index, expense ->
            Entry(index.toFloat(), expense.total_amount.toFloat())
        }
    }
}