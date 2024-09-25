// PaymentTransaction.kt
package com.task.expencetracker.data.dataTransaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_transactions")
data class PaymentTransaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Changed to auto-generate
    val title: String,
    val amount: Double,
    val date: String, // Date should be formatted as a String
    val type: String,
    val description: String
)
