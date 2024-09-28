package com.task.expencetracker.accessibility

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.widget.Toast
import com.task.expencetracker.data.dataTransaction.Transaction
import com.task.expencetracker.viewModel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentNotificationListener : NotificationListenerService() {

    // This function will be called whenever a notification is detected
    private lateinit var viewModel: TransactionViewModel

    // This function can be called from your main activity to set the ViewModel instance
    fun setViewModel(viewModel: TransactionViewModel) {
        this.viewModel = viewModel
    }

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
            handlePaymentNotification(title, text)
        }
    }

    private fun isPaymentNotification(text: String): Boolean {
        val keywords = listOf("debited", "credited", "payment", "transaction", "purchase", "paid", "receipt", "amount")
        return keywords.any { keyword -> text.contains(keyword, ignoreCase = true) }
    }

    private fun handlePaymentNotification(title: String, text: String) {
        Log.d("PaymentNotification", "Payment detected: Title: $title, Text: $text")
        Toast.makeText(this, "Payment detected: $text", Toast.LENGTH_SHORT).show()

        // Extract payment details (for example: amount, type)
        val amount = extractAmountFromText(text)
        val type = if (text.contains("debited", ignoreCase = true)) "Expense" else "Income"

        // Notify ViewModel to save the transaction to the database
        savePaymentDetailsToDatabase(title, text, amount, type)
    }

    private fun savePaymentDetailsToDatabase(title: String, text: String, amount: Double, type: String) {
        // Create a transaction object
        val transaction = Transaction(
            title = title,
            description = text,
            amount = amount,
            type = type,
            timestamp = System.currentTimeMillis(),
        )

        // Notify ViewModel to save the transaction
        viewModel.addTransaction(transaction)
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

    private fun extractAmountFromText(text: String): Double {
        val regex = Regex("""\d+([,.]\d{1,2})?""") // Match any number with optional decimal
        val matchResult = regex.find(text)
        return matchResult?.value?.replace(",", "")?.toDoubleOrNull() ?: 0.0
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
