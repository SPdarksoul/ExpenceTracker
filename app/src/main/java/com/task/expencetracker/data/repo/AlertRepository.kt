package com.task.expencetracker.data.repo

import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.dataTransaction.TransactionAlertEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlertRepository @Inject constructor(private val dao: ExpenseDao) {

    // Fetches all alerts as a Flow of TransactionAlertEntity list
    val alerts: Flow<List<TransactionAlertEntity>> = dao.getAllAlerts()

    // Adds an alert directly as TransactionAlertEntity
    suspend fun addAlert(alert: TransactionAlertEntity) {
        dao.insertAlert(alert)
    }

    // Deletes an alert directly as TransactionAlertEntity
    suspend fun deleteAlert(alert: TransactionAlertEntity) {
        dao.deleteAlert(alert)
    }
}
