package com.task.expencetracker.data.util

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.task.expencetracker.R
import com.task.expencetracker.data.model.ExpenceEntity
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale

object Utils {
    // Date formatters
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    private val inputFormatterDDMM = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US) // New formatter for dd/MM/yyyy
    private val monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.US)
    private val dayFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.US)

    // Format date string to "Month Year"
    fun formatStringDateToMonthYear(date: String?): String {
        if (date.isNullOrEmpty()) return "Invalid Date"
        return try {
            val localDate = LocalDate.parse(date, inputFormatterDDMM) // Use dd/MM/yyyy formatter
            localDate.format(monthYearFormatter)
        } catch (e: DateTimeParseException) {
            println("Date parsing error for '$date': ${e.message}") // Debugging log
            "Invalid Date"
        }
    }

    // Format date string to "Day, Month Day, Year"
    fun formatStringDateToDay(date: String?): String {
        if (date.isNullOrEmpty()) return "Invalid Date"
        return try {
            val localDate = LocalDate.parse(date, inputFormatterDDMM) // Use dd/MM/yyyy formatter
            localDate.format(dayFormatter)
        } catch (e: DateTimeParseException) {
            println("Date parsing error for '$date': ${e.message}") // Debugging log
            "Invalid Date"
        }
    }

    // Format currency amount to string
    fun formatCurrency(amount: Double): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
        return currencyFormat.format(amount)
    }

    // Convert date string to milliseconds (dd/MM/yyyy format)
    fun getMilliFromDate(date: String?): Long {
        if (date.isNullOrEmpty()) {
            println("Date is null or empty.")
            return -1L
        }

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            val parsedDate = formatter.parse(date)
            println("Parsing date: $date")  // Debugging log
            parsedDate?.time ?: -1L
        } catch (e: ParseException) {
            println("Failed to parse date: $date")
            e.printStackTrace()
            -1L
        }
    }

    // Format value to 2 decimal places
    fun formatToDecimalValue(value: Double): String {
        return String.format("%.2f", value)
    }

    // Format milliseconds to date string (dd/MM/yyyy)
    fun formatDateToHumanReadableForm(dateInMillis: Long): String {
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormatter.format(Date(dateInMillis))
    }

    // Format milliseconds to short date string for charts (dd-MMM)
    fun formatDateForChart(dateInMillis: Long): String {
        val dateFormatter = SimpleDateFormat("dd-MMM", Locale.getDefault())
        return dateFormatter.format(Date(dateInMillis))
    }

    // Get item icon based on the title of the expense entity
    fun getItemIcon(item: ExpenceEntity): Int {
        return when (item.title) {
            "Paypal" -> R.drawable.ic_paypal
            "Netflix" -> R.drawable.ic_netflix
            "Starbucks" -> R.drawable.ic_starbucks
            "Salary" -> R.drawable.salary
            "Investment" -> R.drawable.investments
            "OtherIncome" -> R.drawable.ic_starbucks
            else -> R.drawable.otherincome
        }
    }
}