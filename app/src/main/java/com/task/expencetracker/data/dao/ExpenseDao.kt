package com.task.expencetracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.task.expencetracker.data.dataTransaction.AddPaymentTransacton

import com.task.expencetracker.data.dataTransaction.PaymentTransaction
import com.task.expencetracker.data.dataTransaction.Transaction
import com.task.expencetracker.data.dataTransaction.TransactionAlert
import com.task.expencetracker.data.model.ExpenceEntity // Corrected spelling
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    // Group expenses by month and order by date
    @Query("SELECT * FROM expense_table ORDER BY strftime('%Y', date) DESC, strftime('%m', date) DESC, date ASC")
    fun getExpensesGroupedByMonth(): Flow<List<ExpenceEntity>>

    // Retrieve all expenses ordered by date in ascending order
    @Query("SELECT * FROM expense_table ORDER BY date ASC")
    fun getAllExpensesByDate(): Flow<List<ExpenceEntity>> // Corrected spelling

    // Retrieve the top 10 expenses ordered by total amount in descending order
    @Query("SELECT * FROM expense_table ORDER BY total_amount DESC LIMIT 10")
    fun getTopExpenses(): Flow<List<ExpenceEntity>>

    // Retrieve all expenses
    @Query("SELECT * FROM expense_table")
    fun getAllExpenses(): Flow<List<ExpenceEntity>>

    // Insert a new expense into the expense_table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(ExpenceEntity: ExpenceEntity)

    // Delete an expense from the expense_table
    @Delete
    suspend fun deleteExpense(ExpenceEntity: ExpenceEntity)

    // Update an existing expense in the expense_table
    @Update
    suspend fun updateExpense(ExpenceEntity: ExpenceEntity)

    // Retrieve all transactions from the payment_transactions table
    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): Flow<List<Transaction>>

    // Insert a new transaction into the payment_transactions table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: PaymentTransaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertliveTransaction(transaction: Transaction)

    // Retrieve all payments from the payment_table
    @Query("SELECT * FROM payment_table")
    fun getAllPayments(): Flow<List<AddPaymentTransacton>> // Corrected spelling

    // Insert a new payment into the payment_table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: AddPaymentTransacton)

    // Delete a payment from the payment_table by ID
    @Query("DELETE FROM payment_table WHERE id = :id")
    suspend fun deletePayment(id: Long)


    @Query("SELECT * FROM expense_table WHERE strftime('%Y-%m', date) = :month")
    suspend fun getExpensesByMonth(month: String): List<ExpenceEntity>

    @Query("SELECT * FROM expense_table WHERE date = :date")
    suspend fun getExpensesByDate(date: String): List<ExpenceEntity>

    @Query("SELECT * FROM expense_table WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpenceEntity>

    @Insert
    suspend fun addAlert(alert: TransactionAlert)

    @Query("SELECT * FROM transaction_alert_table")
    fun getAllAlerts(): Flow<List<TransactionAlert>>
}
