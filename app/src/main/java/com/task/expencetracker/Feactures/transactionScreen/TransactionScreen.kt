package com.task.expencetracker.features.transactionScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.data.util.Utils
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expencetracker.viewModel.TransactionViewModel
import java.time.LocalDate
import java.time.format.DateTimeParseException

//@Composable
//fun TransactionScreen(
//    viewModel: TransactionViewModel = viewModel(),
//    searchQuery: String = "",
//    selectedMonth: String? = null // Optional filter for the selected month
//) {
//    // Collect transactions state from the ViewModel.
//    val transactions by viewModel.transactions.collectAsStateWithLifecycle(initialValue = emptyList())
//
//    // Display the filtered transaction list.
//    TransactionList(
//        list = transactions,
//        searchQuery = searchQuery,
//        selectedMonth = selectedMonth
//    )
//}

@Composable
fun TransactionList(
    modifier: Modifier = Modifier,
    list: List<ExpenceEntity>,
    title: String = "Recent Transactions",
    searchQuery: String = "",
    selectedMonth: String? = null // Optional filter for the selected month.
) {
    val currentMonth = Utils.formatStringDateToMonthYear(LocalDate.now().toString())

    // Filter and sort the transactions based on the search query and selected month.
    val filteredList = list.filter { transaction ->
        val formattedDate = Utils.formatStringDateToMonthYear(transaction.date)
        val matchesSearch = transaction.title.contains(searchQuery, ignoreCase = true) ||
                transaction.type.contains(searchQuery, ignoreCase = true) ||
                formattedDate.contains(searchQuery, ignoreCase = true)

        val matchesMonth = selectedMonth?.let { month -> formattedDate == month } ?: true

        matchesSearch && matchesMonth
    }.sortedByDescending {
        try {
            Utils.parseDate(it.date)?.toEpochDay() ?: Long.MIN_VALUE // Use epoch day for sorting if valid date is found.
        } catch (e: DateTimeParseException) {
            Long.MIN_VALUE // Push invalid dates to the end.
        }
    }

    // Group transactions by month (month-year format).
    val groupedTransactions = filteredList.groupBy { Utils.formatStringDateToMonthYear(it.date) }

    // Ensure the current month appears first, followed by other months in descending order.
    val sortedGroupedTransactions = groupedTransactions.toSortedMap { month1, month2 ->
        when {
            month1 == currentMonth && month2 != currentMonth -> -1
            month2 == currentMonth && month1 != currentMonth -> 1
            else -> month2.compareTo(month1)
        }
    }

    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        item {
            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        sortedGroupedTransactions.forEach { (month, transactions) ->
            item {
                Text(
                    text = month,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp).background(Color.LightGray, shape = MaterialTheme.shapes.small).padding(8.dp)
                )
                Divider(color = Color.Gray, thickness = 1.dp)
            }

            items(transactions) { transaction ->
                val icon = Utils.getItemIcon(transaction)
                val amountFormatted = Utils.formatCurrency(if (transaction.type == "Income") transaction.total_amount else -transaction.total_amount)

                val formattedDate: String = try {
                    Utils.formatStringDateToDay(transaction.date)
                } catch (e: DateTimeParseException) {
                    "Invalid Date" // Handle invalid date gracefully.
                }

                TransactionItem(
                    title = transaction.title,
                    amount = amountFormatted,
                    icon = icon,
                    date = formattedDate,
                    color = if (transaction.type == "Income") Color.Green else Color.Red,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            item {
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun TransactionItem(
    title: String,
    amount: String,
    icon: Int,
    date: String,
    color: Color,
    modifier: Modifier = Modifier // Added modifier parameter for padding and styling.
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium // Rounded corners for card UI.
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Space between icon and amount/date.
        ) {
            Image(
                painterResource(id=icon),
                contentDescription=null,
                modifier=Modifier.size(40.dp)
            )
            Spacer(modifier=Modifier.width(8.dp))
            Column(
                modifier=Modifier.weight(1f) // Allow column to take available space.
            ) {
                ExpenseTextView(
                    text=title,
                    fontSize=16.sp,
                    fontWeight=FontWeight.Medium
                )
                Spacer(modifier=Modifier.height(4.dp))
                ExpenseTextView(
                    text=date,
                    fontSize=13.sp,
                    color=Color.Gray
                )
            }
            ExpenseTextView(
                text=amount,
                fontSize=18.sp,
                fontWeight=FontWeight.Medium,
                color=color // Dynamic color based on income/expense type.
            )
        }
    }
}

@Composable
fun CardRowItem(modifier: Modifier, title: String, amount: String, image: Int) {
    Column(modifier=modifier) {
        Row {
            Image(
                painterResource(id=image),
                contentDescription=null,
            )
            Spacer(modifier=Modifier.size(8.dp))
            ExpenseTextView(text=title, color=Color.White)
        }
        Spacer(modifier=Modifier.size(4.dp))
        ExpenseTextView(text=amount, color=Color.White)
    }
}