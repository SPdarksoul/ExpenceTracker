package com.task.expencetracker.notification


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.task.expencetracker.features.createExpense.setAlarm

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED && context != null) {
            // The phone has been rebooted; you should reset alarms here

            // For example, fetch saved alarms from a database or SharedPreferences
            // val savedAlarms = fetchAlarmsFromDatabase()

            // Re-set each alarm using AlarmManager
            // Example of setting an alarm
            // savedAlarms.forEach { alarm ->
            //     setAlarm(context, alarm.title, alarm.triggerAtMillis)
            // }
        }
    }
}
