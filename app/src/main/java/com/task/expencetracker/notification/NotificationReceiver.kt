package com.task.expencetracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import android.os.Build

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Extract alert title or any data from the intent (if needed)
        val alertTitle = intent?.getStringExtra("alert_title") ?: "No Title"

        // Create a notification
        if (context != null) {
            showNotification(context, alertTitle)
        }
    }

    private fun showNotification(context: Context, alertTitle: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "ALERT_CHANNEL_ID"

        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Alert Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for alert notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with your own icon
            .setContentTitle("Transaction Alert")
            .setContentText("Alert for: $alertTitle")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // Show the notification
        notificationManager.notify(1, notification)
    }
}
