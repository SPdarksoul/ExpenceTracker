package com.task.expencetracker.accessibility
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val bundle: Bundle? = intent.extras
        val pdus = bundle?.get("pdus") as? Array<*>

        // Check if PDUs are not null
        if (pdus != null) {
            // Iterate through the received PDUs
            for (pdu in pdus) {
                val smsMessage: SmsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                val messageBody = smsMessage.messageBody
                val sender = smsMessage.displayOriginatingAddress

                // Log or display the received SMS for debugging
                Log.d("SmsReceiver", "Received SMS from: $sender, Message: $messageBody")

                // Check if the message body contains payment-related keywords
                if (isPaymentMessage(messageBody)) {
                    handlePaymentMessage(context, sender, messageBody)
                }
            }
        }
    }

    /**
     * Check if the message contains payment-related keywords.
     */
    private fun isPaymentMessage(messageBody: String): Boolean {
        // List of keywords that indicate a payment message
        val keywords = listOf("payment", "debited", "credited", "transaction", "purchase", "amount", "paid")

        // Check if any of the keywords exist in the message
        return keywords.any { keyword -> messageBody.contains(keyword, ignoreCase = true) }
    }

    /**
     * Handle detected payment message.
     */
    private fun handlePaymentMessage(context: Context, sender: String, messageBody: String) {
        // Log detected payment
        Log.d("SmsReceiver", "Payment detected from $sender: $messageBody")

        // Display a notification or process the payment
        Toast.makeText(context, "Payment detected from $sender", Toast.LENGTH_SHORT).show()

        // Here you can save the message to a local database, show a notification, or perform other actions
        // Example: Save to database or trigger UI update
    }
}
