package com.task.expencetracker.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.model.ExpenceEntity

import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(val dao: ExpenseDao) : ViewModel() {

    suspend fun addExpense(ExpenceEntity: ExpenceEntity): Boolean {
        return try {
            dao.insertExpense(ExpenceEntity)
            true
        } catch (ex: Throwable) {
            false
        }
    }
}

