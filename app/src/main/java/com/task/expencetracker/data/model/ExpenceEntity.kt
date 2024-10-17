package com.task.expencetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class ExpenceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null, // Nullable for auto-generated values
    val title: String,
    val total_amount: Double,
    val date: String,
    val type: String
)


