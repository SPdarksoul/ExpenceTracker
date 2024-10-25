package com.task.expencetracker.data.dataTransaction

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transaction_alert_table")
data class TransactionAlert(
    @PrimaryKey(autoGenerate = true) val id: String = 0, // Use Long for auto-generated IDs
    val title: String,
    val amount: Long, // Use Double for more accurate monetary representation
    val dateTime: Long // Keep as Long for timestamp
)