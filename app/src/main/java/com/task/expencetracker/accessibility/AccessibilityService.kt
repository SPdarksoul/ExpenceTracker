package com.task.expencetracker.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import com.task.expencetracker.data.dataTransaction.PaymentTransaction
import com.task.expencetracker.database.ExpenseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AccessibilityService : AccessibilityService() {

    // Lazy initialization of the database
    private val db by lazy { ExpenseDatabase.getInstance(this) }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Ensure the event is not null and is a notification event
        event?.let {
            if (it.eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                val notificationText = it.text.joinToString(separator = " ")
                Log.d("PaymentAccessibility", "Notification text: $notificationText")

                // Check if the notification contains payment-related keywords
                if (isPaymentNotification(notificationText)) {
                    handlePaymentNotification(notificationText)
                }
            }
        }
    }

    // Check if the notification text contains payment-related keywords
    private fun isPaymentNotification(text: String): Boolean {
        val keywords = listOf("payment", "debited", "credited", "transaction", "purchase", "paid", "amount")
        return keywords.any { keyword -> text.contains(keyword, ignoreCase = true) }
    }

    // Extract payment details from the notification and return a PaymentTransaction object
    private fun extractPaymentDetails(text: String): PaymentTransaction {
        val amount = extractAmountFromText(text)
        val name = extractNameFromText(text)
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val title = "Payment"  // Default title
        val type = "Expense"   // Assuming all transactions are expenses

        return PaymentTransaction(
            title = title,
            amount = amount,
            date = date,
            type = type,
            description = name
        )
    }

    // Extract the amount from the notification using regex
    private fun extractAmountFromText(text: String): Double {
        val regex = Regex("\\d+\\.\\d+")
        return regex.find(text)?.value?.toDouble() ?: 0.0
    }

    // Extract a name or description from the notification text
    private fun extractNameFromText(text: String): String {
        return Regex("[a-zA-Z]+").find(text)?.value ?: "Unknown"
    }

    // Save the payment details into the database using a coroutine for background execution
    private fun savePaymentDetailsToDatabase(transaction: PaymentTransaction) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.expenseDao().insertTransaction(transaction)
                Log.d("PaymentAccessibility", "Payment details saved: $transaction")
            } catch (e: Exception) {
                Log.e("PaymentAccessibility", "Error saving payment details: ${e.message}", e)
            }
        }
    }

    // Handle the payment notification, extract details, and save them to the database
    private fun handlePaymentNotification(text: String) {
        Log.d("PaymentAccessibility", "Payment detected: $text")
        Toast.makeText(this, "Payment detected: $text", Toast.LENGTH_SHORT).show()

        val transaction = extractPaymentDetails(text)
        savePaymentDetailsToDatabase(transaction)

        // Broadcast an intent to notify the rest of the app about the new transaction
        sendBroadcast(Intent("com.task.expensetracker.NEW_TRANSACTION").apply {
            putExtra("title", "Payment Detected")
            putExtra("text", transaction.description)
        })
    }

    // Handle interruptions to the accessibility service
    override fun onInterrupt() {
        Log.d("PaymentAccessibility", "Accessibility service interrupted")
    }

    // Setup the service configuration when the accessibility service is connected
    override fun onServiceConnected() {
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            notificationTimeout = 100
            packageNames = null // Listen to notifications from all apps, adjust if needed
            flags = AccessibilityServiceInfo.DEFAULT
        }
        serviceInfo = info
        Log.d("PaymentAccessibility", "Accessibility Service Connected")
    }

    // Handle service destruction and clean-up if necessary
    override fun onDestroy() {
        super.onDestroy()
        Log.d("PaymentAccessibility", "Accessibility Service Destroyed")
    }
}


