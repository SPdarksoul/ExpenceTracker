package com.task.expencetracker.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.task.expencetracker.data.dataTransaction.TransactionAlert
import com.task.expencetracker.notifications.NotificationReceiver


// Cancel notification
fun cancelNotification(context: Context, alert: TransactionAlert) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        (alert.id ?: 0).toInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(pendingIntent)
}
