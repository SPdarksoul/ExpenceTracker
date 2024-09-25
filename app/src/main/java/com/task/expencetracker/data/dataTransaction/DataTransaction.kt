package com.task.expencetracker.data.dataTransaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val recipient: String,
    val amount: String
)
