package com.task.expencetracker.data.repo

import com.task.expencetracker.data.dataTransaction.TransactionAlert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.ConcurrentHashMap

interface AlertRepository {
    suspend fun addAlert(alert: TransactionAlert)
    suspend fun getAlerts(): Flow<List<TransactionAlert>>
    suspend fun deleteAlert(alert: TransactionAlert)
}

class AlertRepositoryImpl : AlertRepository {

    // In-memory storage for demonstration; replace with actual data source (e.g., Room or Retrofit)
    private val alerts = ConcurrentHashMap<Long, TransactionAlert>()

    override suspend fun addAlert(alert: TransactionAlert) {
        alerts[alert.dateTime] = alert // Using dateTime as a unique key for simplicity
    }

    override suspend fun getAlerts(): Flow<List<TransactionAlert>> {
        return flow {
            emit(alerts.values.toList()) // Emit current list of alerts
        }
    }

    override suspend fun deleteAlert(alert: TransactionAlert) {
        alerts.remove(alert.dateTime) // Remove alert by dateTime key
    }
}