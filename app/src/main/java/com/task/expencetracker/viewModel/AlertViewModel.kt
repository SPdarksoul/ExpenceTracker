package com.task.expencetracker.viewModel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.task.expencetracker.data.dataTransaction.TransactionAlertEntity
import com.task.expencetracker.data.repo.AlertRepository
import com.task.expencetracker.notification.NotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    application: Application,
    private val alertRepository: AlertRepository
) : AndroidViewModel(application) {

    private val _alerts = MutableStateFlow<List<TransactionAlertEntity>>(emptyList())
    val alerts = _alerts.asStateFlow()

    init {
        fetchAlerts()
        createNotificationChannel()
    }
    fun deleteAlert(alert: TransactionAlertEntity) {
        viewModelScope.launch {
            try {
                alertRepository.deleteAlert(alert)
                fetchAlerts() // Refresh the alerts list after deleting
            } catch (e: Exception) {
                // Handle any errors here (e.g., logging)
            }
        }
    }

    private fun fetchAlerts() {
        viewModelScope.launch {
            alertRepository.alerts.collect { fetchedAlerts ->
                _alerts.value = fetchedAlerts
                scheduleNotificationsForAlerts(fetchedAlerts)
            }
        }
    }

    private fun scheduleNotificationsForAlerts(alerts: List<TransactionAlertEntity>) {
        val workManager = WorkManager.getInstance(getApplication<Application>())

        alerts.forEach { alert ->
            val delay = alert.dateTime - System.currentTimeMillis()
            if (delay > 0) {
                val alertData = workDataOf(
                    "alert_title" to alert.title,
                    "alert_amount" to alert.amount,
                    "alert_date" to SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(alert.dateTime))
                )

                val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(alertData)
                    .build()

                workManager.enqueueUniqueWork(
                    "Alert_${alert.id}", // Unique work name to prevent duplicates
                    ExistingWorkPolicy.REPLACE,
                    notificationWork
                )
            }
        }
    }

    fun addAlert(alert: TransactionAlertEntity) {
        viewModelScope.launch {
            alertRepository.addAlert(alert)
            fetchAlerts() // Refresh alerts to include the new one
        }
    }

    private fun createNotificationChannel() {
        val context = getApplication<Application>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ALERT_CHANNEL_ID",
                "Transaction Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for transaction alerts"
            }
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
