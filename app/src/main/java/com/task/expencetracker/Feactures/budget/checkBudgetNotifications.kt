package com.task.expencetracker.features.budget

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.task.expencetracker.Feactures.budget.BudgetEntity

fun checkBudgetNotifications(context: Context, budgets: List<BudgetEntity>) {
    budgets.forEach { budget ->
        when {
            budget.spentAmount > budget.allocatedAmount -> {
                // Budget exceeded
                showNotification(
                    context,
                    "Budget Exceeded",
                    "You've exceeded your budget for ${budget.category}!"
                )
            }
            budget.spentAmount > 0.9 * budget.allocatedAmount -> {
                // Near budget limit
                showNotification(
                    context,
                    "Budget Alert",
                    "You're close to your budget limit for ${budget.category}!"
                )
            }
        }
    }
}

fun showNotification(context: Context, title: String, message: String) {
    val notificationManager = NotificationManagerCompat.from(context)

    // Create a notification channel for Android versions >= 8.0 (API 26)
    val channelId = "expense_tracker_notifications"
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Expense Tracker Alerts",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifications for budget alerts"
        }
        notificationManager.createNotificationChannel(channel)
    }

    // Build and display the notification
    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    notificationManager.notify(1, notification)
}
