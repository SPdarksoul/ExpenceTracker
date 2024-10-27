package com.task.expencetracker.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.task.expencetracker.R
import com.task.expencetracker.data.dataTransaction.TransactionAlert
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Extract data from the intent
        val alert = intent.getParcelableExtra<TransactionAlert>("ALERT_DATA")

        // Check if alert is not null
        alert?.let {
            sendNotification(context, it)
        }
    }

    private fun sendNotification(context: Context, alert: TransactionAlert) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationMessage = "Transaction Alert Details:\n" +
                "Name: ${alert.title}\n" +
                "Amount: $${alert.amount}\n" +
                "Date: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(alert.dateTime))}"

        val notification = NotificationCompat.Builder(context, "ALERT_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Transaction Alert Passed")
            .setContentText("An alert has passed.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationMessage))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(alert.hashCode(), notification)
    }
}