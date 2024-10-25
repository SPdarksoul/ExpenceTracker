package com.task.expencetracker.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.task.expencetracker.data.dataTransaction.TransactionAlert
import com.task.expencetracker.notifications.NotificationReceiver

// Schedule notification with AlarmManager
fun scheduleNotification(context: Context, alert: TransactionAlert) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("title", alert.title)
        putExtra("amount", alert.amount)
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        (alert.id ?: 0).toInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        alert.dateTime,
        pendingIntent
    )
}