package com.task.expencetracker.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.task.expencetracker.R
import com.task.expencetracker.data.dataTransaction.TransactionAlertEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Extract TransactionAlertEntity data from the intent
        val alert = intent.getParcelableExtra<TransactionAlertEntity>("ALERT_DATA")

        // Check if alert data is available before proceeding
        alert?.let {
            sendNotification(context, it)
        }
    }

     private fun sendNotification(context: Context, alert: TransactionAlertEntity) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ALERT_CHANNEL_ID",
                "Transaction Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for transaction alerts"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Format the notification message
        val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(alert.dateTime))
        val notificationMessage = """
            Transaction Alert Details:
            Name: ${alert.title}
            Amount: $${alert.amount}
            Date: $formattedDate
        """.trimIndent()

        // Build and send the notification
        val notification = NotificationCompat.Builder(context, "ALERT_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_notification)  // Ensure this drawable exists
            .setContentTitle("Transaction Alert")
            .setContentText("An alert has passed.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationMessage))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(alert.hashCode(), notification)
    }
}
