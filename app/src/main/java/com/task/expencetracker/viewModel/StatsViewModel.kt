package com.task.expencetracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.model.ExpenseSummary
import com.task.expencetracker.data.model.mapEntityToSummary
import com.task.expencetracker.data.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(private val dao: ExpenseDao) : ViewModel() {

    private val _entries = MutableStateFlow<List<ExpenseSummary>>(emptyList())
    val entries: StateFlow<List<ExpenseSummary>> = _entries.asStateFlow()

    init {
        viewModelScope.launch {
            // Fetch all data from the database and map it
            val expenseList = dao.getAllExpensesByDate().first() // Assuming this returns a Flow
            _entries.value = expenseList.map { mapEntityToSummary(it) }
        }
    }

    fun getEntriesForChart(entries: List<ExpenseSummary>): List<Entry> {
        return entries.map { entry ->
            val formattedDate = Utils.getMilliFromDate(entry.date)
            Entry(formattedDate.toFloat(), entry.total_amount.toFloat())
        }
    }


}
