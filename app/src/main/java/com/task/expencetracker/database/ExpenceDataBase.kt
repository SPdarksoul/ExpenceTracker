// ExpenseDatabase.kt
package com.task.expencetracker.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.expencetracker.data.dao.ExpenseDao

import com.task.expencetracker.data.dataTransaction.AddPaymentTransacton
import com.task.expencetracker.data.dataTransaction.PaymentTransaction
import com.task.expencetracker.data.dataTransaction.Transaction
import com.task.expencetracker.data.model.ExpenceEntity

@Database(
    entities = [ExpenceEntity::class, PaymentTransaction::class, AddPaymentTransacton::class, Transaction ::class],
    version = 7,
    exportSchema = false // Disable schema export
)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getInstance(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_database"
                )
                    .fallbackToDestructiveMigration() // Use cautiously
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
