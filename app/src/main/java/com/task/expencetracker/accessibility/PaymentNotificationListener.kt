package com.task.expencetracker.accessibility

import android.app.Notification
import android.content.Context
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.widget.Toast

class PaymentNotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notification = sbn.notification
        val extras = notification.extras

        // Extract title and text from the notification
        val title = extras.getString(Notification.EXTRA_TITLE) ?: "No Title"
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: "No Text"

        // Log the notification content for debugging
        Log.d("PaymentNotification", "Title: $title, Text: $text")

        // Check if the notification text contains payment-related keywords
        if (isPaymentNotification(text)) {
            handlePaymentNotification(this, title, text)
        }
    }

    private fun isPaymentNotification(text: String): Boolean {
        val keywords = listOf("debited", "credited", "payment", "transaction", "purchase", "paid", "receipt", "amount")
        return keywords.any { keyword -> text.contains(keyword, ignoreCase = true) }
    }

    private fun handlePaymentNotification(context: Context, title: String, text: String) {
        Log.d("PaymentNotification", "Payment detected: Title: $title, Text: $text")
        Toast.makeText(context, "Payment detected: $text", Toast.LENGTH_SHORT).show()
        savePaymentDetailsToDatabase(title, text)
    }

    private fun savePaymentDetailsToDatabase(title: String, text: String) {
        Log.d("PaymentNotification", "Saving payment details to database: Title: $title, Text: $text")
        // TODO: Add actual database saving logic here
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d("PaymentNotification", "Notification removed: ${sbn.packageName}")
    }

    override fun onListenerConnected() {
        Log.d("PaymentNotification", "Notification Listener Connected")
    }

    override fun onListenerDisconnected() {
        Log.d("PaymentNotification", "Notification Listener Disconnected")
    }
}
