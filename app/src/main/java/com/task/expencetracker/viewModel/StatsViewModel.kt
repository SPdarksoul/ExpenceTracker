package com.task.expencetracker.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.data.model.mapSummaryToEntity

import com.task.expencetracker.data.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(private val dao: ExpenseDao) : ViewModel() {

    private val _entries = MutableStateFlow<List<ExpenceEntity>>(emptyList())
    val entries: StateFlow<List<ExpenceEntity>> = _entries.asStateFlow()

    init {
        viewModelScope.launch {
            // Fetch all data from the database and map it
            val expenseList = dao.getAllExpensesByDate().first() // Assuming this returns a Flow
            _entries.value = expenseList.map { mapSummaryToEntity(it) }
        }
    }

    fun getEntriesForChart(entries: List<ExpenceEntity>): List<Entry> {
        return entries.map { entry ->
            val formattedDate = Utils.getMilliFromDate(entry.date)
            Entry(formattedDate.toFloat(), entry.total_amount.toFloat())
        }
    }


    // Flow to observe expenses
    val expenses: Flow<List<ExpenceEntity>> = dao.getAllExpenses()

     fun getMilliFromDate(date: String?): Long {
        if (date.isNullOrEmpty()) return -1L
        val cleanedDate = date.trim()  // Remove leading/trailing spaces
        Log.d("DateParser", "Raw date received: $cleanedDate")

        val formats = listOf(
            "dd/MM/yyyy",  // Expected format
            "yyyy-MM-dd",  // ISO format
            "MM/dd/yyyy"   // US format
        )

        for (format in formats) {
            try {
                val formatter = SimpleDateFormat(format, Locale.getDefault())
                val parsedDate = formatter.parse(cleanedDate)
                if (parsedDate != null) {
                    Log.d("DateParser", "Successfully parsed date: $cleanedDate with format: $format")
                    return parsedDate.time
                }
            } catch (e: ParseException) {
                Log.d("DateParser", "Failed to parse with format: $format")
            }
        }

        Log.e("DateParser", "Failed to parse date: $cleanedDate")
        return -1L
    }

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
