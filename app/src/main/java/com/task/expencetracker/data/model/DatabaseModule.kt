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

    @Provides
    @Singleton
    fun provideExpenseDatabase(@ApplicationContext context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expense_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideExpenseDao(expenseDatabase: ExpenseDatabase): ExpenseDao {
        return expenseDatabase.expenseDao()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(expenseDao: ExpenseDao): TransactionRepository {
        return TransactionRepository(expenseDao)
    }
}