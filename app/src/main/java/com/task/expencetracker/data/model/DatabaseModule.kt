package com.task.expencetracker.data.model

import android.content.Context
import androidx.room.Room
import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.repo.TransactionRepository
import com.task.expencetracker.database.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // Provide a single instance of ExpenseDatabase
    @Provides
    @Singleton
    fun provideExpenseDatabase(@ApplicationContext context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expense_tracker_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    // Provide the DAO for ExpenseDatabase
    @Provides
    fun provideExpenseDao(expenseDatabase: ExpenseDatabase): ExpenseDao {
        return expenseDatabase.expenseDao()
    }

    // Provide the TransactionRepository with ExpenseDao
    @Provides
    @Singleton
    fun provideTransactionRepository(expenseDao: ExpenseDao): TransactionRepository {
        return TransactionRepository(expenseDao)
    }
}
