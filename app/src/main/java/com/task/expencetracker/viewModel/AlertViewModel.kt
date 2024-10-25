package com.task.expencetracker.viewModel

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.task.expencetracker.R
import com.task.expencetracker.data.dataTransaction.TransactionAlert
import com.task.expencetracker.data.repo.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    application: Application,  // Inject Application context
    private val alertRepository: AlertRepository // Injecting the repository
) : AndroidViewModel(application) {

    private val _alerts = MutableStateFlow<List<TransactionAlert>>(emptyList())
    val alerts = _alerts.asStateFlow()

    init {
        fetchAlerts()
        checkForExpiredAlerts() // Start checking for expired alerts
    }

    private fun fetchAlerts() {
        viewModelScope.launch {
            alertRepository.getAlerts().collect { fetchedAlerts ->
                _alerts.value = fetchedAlerts // Update state with fetched alerts
            }
        }
    }

    fun addAlert(alert: TransactionAlert) {
        viewModelScope.launch {
            alertRepository.addAlert(alert)
            fetchAlerts() // Refresh the list after adding an alert
        }
    }

    fun deleteAlert(alert: TransactionAlert) {
        viewModelScope.launch {
            alertRepository.deleteAlert(alert)
            fetchAlerts() // Refresh the list after deletion
        }
    }

    // Check for alerts whose time has passed and notify user.
    private fun checkForExpiredAlerts() {
        viewModelScope.launch {
            _alerts.value.forEach { alert ->
                if (alert.dateTime < System.currentTimeMillis()) {
                    sendNotification(alert)
                    settleAlert(alert) // Automatically settle passed alerts.
                }
            }
        }
    }

    // Settle (delete) an alert.
    fun settleAlert(alert: TransactionAlert) {
        viewModelScope.launch {
            deleteAlert(alert) // Settling by removing the alert.
        }
    }

    // Send notification for expired alerts.
    private fun sendNotification(alert: TransactionAlert) {
        val notificationManager = getApplication<Application>().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(getApplication(), "ALERT_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_notification) // Replace with your app icon.
            .setContentTitle("Transaction Alert Passed")
            .setContentText("Alert for ${alert.title} has passed.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(alert.hashCode(), notification) // Unique notification ID.
    }
}