package com.task.expencetracker.data.dataTransaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "transaction_alerts")
data class TransactionAlert(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val amount: String, // Change to Double if needed
    val dateTime: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(amount)
        parcel.writeLong(dateTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionAlert> {
        override fun createFromParcel(parcel: Parcel): TransactionAlert {
            return TransactionAlert(parcel)
        }

        override fun newArray(size: Int): Array<TransactionAlert?> {
            return arrayOfNulls(size)
        }
    }
}