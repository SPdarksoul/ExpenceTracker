package com.task.expencetracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.data.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val dao: ExpenseDao) : ViewModel() {

    // Flow to observe expenses
    val expenses: Flow<List<ExpenceEntity>> = dao.getAllExpenses()

//    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private fun getMilliFromDate(date: String?): Long {
        if (date.isNullOrEmpty()) return -1L
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            val parsedDate = formatter.parse(date)
            parsedDate?.time ?: -1L
        } catch (e: ParseException) {
            e.printStackTrace()
            -1L
        }
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

    fun getTotalExpense(onResult: (String) -> Unit) {
        viewModelScope.launch {
            expenses.collect { list ->
                val sortedList = sortExpensesByDate(list)
                var total = 0.0
                for (expense in sortedList) {
                    if (expense.type != "Income") {
                        total += expense.total_amount
                    }
                }
                onResult(Utils.formatCurrency(total))
            }
        }
    }

    fun getTotalIncome(onResult: (String) -> Unit) {
        viewModelScope.launch {
            expenses.collect { list ->
                val sortedList = sortExpensesByDate(list)
                var totalIncome = 0.0
                for (expense in sortedList) {
                    if (expense.type == "Income") {
                        totalIncome += expense.total_amount
                    }
                }
                onResult(Utils.formatCurrency(totalIncome))
            }
        }
    }

    fun getBalance(onResult: (String) -> Unit) {
        viewModelScope.launch {
            expenses.collect { list ->
                val sortedList = sortExpensesByDate(list)
                var balance = 0.0
                for (expense in sortedList) {
                    balance += if (expense.type == "Income") {
                        expense.total_amount
                    } else {
                        -expense.total_amount
                    }
                }
                onResult(Utils.formatCurrency(balance))
            }
        }
    }
}
