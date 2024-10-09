package com.task.expencetracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.data.repo.ExpenseRepository
import com.github.mikephil.charting.data.Entry
import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.util.Utils.getMilliFromDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel // Ensure this annotation is present for Hilt to work properly
class StatsViewModel @Inject constructor(private val repository: ExpenseRepository,val dao: ExpenseDao) : ViewModel() {
    private val _entries = MutableStateFlow<List<ExpenceEntity>>(emptyList())
    val entries: StateFlow<List<ExpenceEntity>> = _entries

    // Load expenses for a specific month (format: yyyy-MM)
    fun loadExpensesForMonth(month: String) {
        viewModelScope.launch {
            val expenses = repository.getExpensesByMonth(month)
            _entries.value = expenses // This should be a List<ExpenceEntity>
        }
    }

    // Load expenses based on a date range (from startDate to endDate)
    fun loadExpensesForDateRange(startDate: String, endDate: String) {
        viewModelScope.launch {
            val expenses = repository.getExpensesBetweenDates(startDate, endDate)
            _entries.value = expenses // This should also be a List<ExpenceEntity>
        }
    }

    // Function to format data for the LineChart
    fun getEntriesForChart(expenses: List<ExpenceEntity>): List<Entry> {
        return expenses.map { expense ->
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = LocalDate.parse(expense.date, dateFormatter)
            Entry(localDate.toEpochDay().toFloat(), expense.total_amount.toFloat())
        }
    }

    // Flow to observe expenses
    val expenses: Flow<List<ExpenceEntity>> = dao.getAllExpenses()
    private fun sortExpensesByDate(list: List<ExpenceEntity>): List<ExpenceEntity> {
        return list.sortedBy {
            val millis = getMilliFromDate(it.date)
            if (millis == -1L) Long.MAX_VALUE else millis
        }
    }


    fun getSortedExpenses(): Flow<List<ExpenceEntity>> {
        return expenses.map { list -> sortExpensesByDate(list) }
    }
}