package com.task.expencetracker.data.dataTransaction
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_alerts")
data class TransactionAlert(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,  // Changed id to Long for autoGenerate to work
    val title: String,
    val amount: String,
    val dateTime: Long
)
