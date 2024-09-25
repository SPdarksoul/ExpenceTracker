package com.task.expencetracker.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.util.Utils
import com.task.expencetracker.data.model.ExpenceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseDao
) : ViewModel() {

    private val _allExpenses = MutableLiveData<List<ExpenceEntity>>()
    val allExpenses: LiveData<List<ExpenceEntity>> = _allExpenses

    private val _filteredExpenses = MutableLiveData<List<ExpenceEntity>>()
    val filteredExpenses: LiveData<List<ExpenceEntity>> = _filteredExpenses

    init {
        loadExpenses()
    }

    fun updateSearchQuery(query: String) {
        _filteredExpenses.value = if (query.isBlank()) {
            _allExpenses.value
        } else {
            _allExpenses.value?.filter { expense ->
                expense.title.contains(query, ignoreCase = true) ||
                        expense.type.contains(query, ignoreCase = true) ||
                        Utils.formatStringDateToMonthYear(expense.date).contains(query, ignoreCase = true)
            }
        }
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            repository.getAllExpenses().collect { expenses ->
                _allExpenses.value = expenses
                _filteredExpenses.value = expenses // Initialize filtered with all expenses
            }
        }
    }
}
