package com.task.expencetracker.Feactures.budget


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_table")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val allocatedAmount: Double,
    var spentAmount: Double = 0.0
)
