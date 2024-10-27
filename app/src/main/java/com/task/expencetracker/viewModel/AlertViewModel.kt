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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    application: Application,
    private val alertRepository: AlertRepository
) : AndroidViewModel(application) {

    private val _alerts = MutableStateFlow<List<TransactionAlert>>(emptyList())
    val alerts = _alerts.asStateFlow()

    init {
        fetchAlerts()
        checkForExpiredAlerts()
    }

    private fun fetchAlerts() {
        viewModelScope.launch {
            alertRepository.getAlerts().collect { fetchedAlerts ->
                _alerts.value = fetchedAlerts
            }
        }
    }

    fun addAlert(alert: TransactionAlert) {
        viewModelScope.launch {
            alertRepository.addAlert(alert)
            fetchAlerts()
        }
    }

    fun deleteAlert(alert: TransactionAlert) {
        viewModelScope.launch {
            alertRepository.deleteAlert(alert)
            fetchAlerts()
        }
    }

    private fun checkForExpiredAlerts() {
        viewModelScope.launch {
            _alerts.value.forEach { alert ->
                if (alert.dateTime < System.currentTimeMillis()) {
                    sendNotification(alert)
                    settleAlert(alert)
                }
            }
        }
    }

    fun settleAlert(alert: TransactionAlert) {
        viewModelScope.launch {
            deleteAlert(alert)
        }
    }

    private fun sendNotification(alert: TransactionAlert) {
        val notificationManager = getApplication<Application>().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationMessage = "Transaction Alert Details:\n" +
                "Name: ${alert.title}\n" +
                "Amount: $${alert.amount}\n" +
                "Date: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(alert.dateTime))}"

        val notification = NotificationCompat.Builder(getApplication(), "ALERT_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Transaction Alert Passed")
            .setContentText("An alert has passed.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationMessage))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(alert.hashCode(), notification)
    }
}