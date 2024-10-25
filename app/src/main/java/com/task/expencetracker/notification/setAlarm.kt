package com.task.expencetracker.features.createExpense

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.task.expencetracker.notifications.NotificationReceiver


fun setAlarm(context: Context, alertTitle: String, triggerAtMillis: Long) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("alert_title", alertTitle)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alertTitle.hashCode(), // Unique requestCode for each alert
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // Set the alarm to trigger at the specified time
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP, // Use RTC_WAKEUP to wake up the device if needed
        triggerAtMillis,
        pendingIntent
    )
}
