package com.task.expencetracker.accessibility

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.widget.Toast
import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.data.repo.ExpenseRepository
import com.task.expencetracker.data.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class PaymentNotificationListener : NotificationListenerService() {

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var repository: ExpenseRepository

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notification = sbn.notification ?: return
        val extras = notification.extras

        val title = extras.getString(Notification.EXTRA_TITLE) ?: "No Title"
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: "No Text"

        // Attempt to extract sender information from the title or text.
        val sender = extractSender(title, text)

        Log.d("PaymentNotification", "Title: $title, Text: $text, Sender: $sender")

        if (isPaymentNotification(text)) {
            handlePaymentNotification(title, text, sender)
        }
    }

    private fun extractSender(title: String, text: String): String {
        return if (title.contains("from", ignoreCase = true)) {
            title.substringAfter("from").trim()
        } else {
            "Unknown Sender"
        }
    }

    private fun isPaymentNotification(text: String): Boolean {
        val keywords = listOf("debited", "credited", "payment", "transaction", "purchase", "paid", "receipt", "amount", "received", "deposited", "deducted", "added")
        return keywords.any { keyword -> text.contains(keyword, ignoreCase = true) }
    }

    private fun handlePaymentNotification(title: String, text: String, sender: String) {
        Log.d("PaymentNotification", "Payment detected: Title: $title, Text: $text")

        val amount = extractAmountFromText(text)
        val extractedDateString = extractDateFromText(text)

        if (extractedDateString == null) {
            Log.w("PaymentNotification", "No valid date found in notification text.")
            return
        }

        // Format the extracted date string to a standard format for storage.
        val extractedDate = formatExtractedDate(extractedDateString) ?: getCurrentDate()

        if (amount > 0) {
            val type = if (text.contains("debited", ignoreCase = true)) "Expense" else "Income"

            savePaymentDetailsToDatabase(title, amount, extractedDate, type, sender)
            showToast("Payment detected from $sender: $text")
        } else {
            Log.w("PaymentNotification", "No valid amount found in notification text.")
        }
    }

    private fun savePaymentDetailsToDatabase(title: String, amount: Double, date: String, type: String, sender: String) {
        val expense = ExpenceEntity(
            title = "$title from $sender",
            total_amount = amount,
            date = date,
            type = type
        )

        serviceScope.launch {
            repository.insertExpense(expense)
        }
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
        val regex = Regex("""\d+([,.]\d{1,2})?""")
        val matchResult = regex.find(text)
        return matchResult?.value?.replace(",", "")?.toDoubleOrNull() ?: 0.0
    }

    private fun extractDateFromText(text: String): String? {
        // Regex patterns to match various date formats including time.
        val regexPatterns = listOf(
            """(\d{2}-\d{2}-\d{4} \d{2}:\d{2}:\d{2})""", // Matches dd-MM-yyyy HH:mm:ss format.
            """(\d{2}-\d{2}-\d{4})""",                  // Matches dd-MM-yyyy format.
            """(\d{4}-\d{2}-\d{2})""",                  // Matches yyyy-MM-dd format.
            """(\d{1,2}/\d{1,2}/\d{2,4})"""              // Matches MM/dd/yyyy or dd/MM/yyyy format.
        )

        for (pattern in regexPatterns) {
            val regex = Regex(pattern)
            regex.find(text)?.let { return it.value }
        }

        return null // Return null if no date is found.
    }

    private fun formatExtractedDate(dateString: String): String? {
        return try {
            // Define the expected input date formats.
            val inputFormatWithTime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val inputFormatWithoutTime = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            // Define the output date format for storage in the database.
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            // Try parsing with time first.
            var date = inputFormatWithTime.parse(dateString)
            if (date == null) {
                // If parsing with time fails, try without time.
                date = inputFormatWithoutTime.parse(dateString)
            }

            outputFormat.format(date)
        } catch (e: Exception) {
            Log.e("PaymentNotification", "Error parsing date: ${e.message}")
            null
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun showToast(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(this@PaymentNotificationListener, message, Toast.LENGTH_SHORT).show()
        }
    }
}