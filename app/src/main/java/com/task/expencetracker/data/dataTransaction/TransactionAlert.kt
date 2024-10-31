package com.task.expencetracker.data.dataTransaction

import androidx.room.Entity
import androidx.room.PrimaryKey

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "transaction_alerts")
data class TransactionAlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val amount: String,
    val dateTime: Long
) : Parcelable
