package com.task.expencetracker.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add_expense_table")
data class AddExpenceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "type") val type: String
)
