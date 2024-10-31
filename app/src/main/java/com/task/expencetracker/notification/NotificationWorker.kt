package com.task.expencetracker.notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.task.expencetracker.R

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val alertTitle = inputData.getString("alert_title") ?: "Transaction Alert"
        val alertAmount = inputData.getDouble("alert_amount", 0.0)
        val alertDate = inputData.getString("alert_date") ?: "N/A"

        sendNotification(alertTitle, alertAmount, alertDate)
        return Result.success()
    }

    private fun sendNotification(title: String, amount: Double, date: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

        val notificationMessage = "Transaction Alert Details:\nName: $title\nAmount: $$amount\nDate: $date"

        val notification = NotificationCompat.Builder(applicationContext, "ALERT_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Transaction Alert")
            .setContentText("An alert has passed.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationMessage))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(title.hashCode(), notification)
    }
}
