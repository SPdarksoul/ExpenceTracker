package com.task.expencetracker.features.transactionScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.data.util.Utils
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expencetracker.viewModel.TransactionViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun TransactionList(
    modifier: Modifier = Modifier,
    list: List<ExpenceEntity>,
    title: String = "Recent Transactions",
    searchQuery: String = "",
    selectedMonth: String? = null // Optional filter for the selected month
) {
    // Get the current month in the same format as the date
    val currentMonth = Utils.formatStringDateToMonthYear(LocalDate.now().toString())

    // Filter transactions based on search query and selected month
    val filteredList = list.filter {
        val formattedDate = Utils.formatStringDateToMonthYear(it.date)
        val matchesSearchQuery = it.title.contains(searchQuery, ignoreCase = true) ||
                it.type.contains(searchQuery, ignoreCase = true) ||
                formattedDate.contains(searchQuery, ignoreCase = true)

        // Filter based on selected month if provided
        val matchesSelectedMonth = selectedMonth?.let { month ->
            formattedDate == month
        } ?: true

        matchesSearchQuery && matchesSelectedMonth
    }

    // Sort transactions by date in descending order (latest first)
    val sortedList = filteredList.sortedByDescending {
        try {
            LocalDate.parse(it.date, Utils.inputFormatter)
        } catch (e: DateTimeParseException) {
            LocalDate.MIN // Handle invalid dates
        }
    }

    // Group transactions by month
    val groupedByMonth = sortedList.groupBy {
        Utils.formatStringDateToMonthYear(it.date) // Group by month-year format
    }

    // Create a sorted map with the current month on top
    val sortedGroupedByMonth = groupedByMonth.toSortedMap { a, b ->
        when {
            a == currentMonth && b != currentMonth -> -1 // Current month should come first
            b == currentMonth && a != currentMonth -> 1 // Ensure current month comes before other months
            else -> b.compareTo(a) // Sort remaining months in descending order
        }
    }

    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        item {
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    if (title == "Recent Transactions") {

                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Iterate through the grouped transactions by month
        sortedGroupedByMonth.forEach { (month, transactions) ->
            item {
                // Header for each month
                Text(
                    text = month,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                // Add a separator line before the month's transactions
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Iterate through each transaction in the month
            items(transactions.size) { index ->
                val item = transactions[index]
                val icon = Utils.getItemIcon(item)
                val amount = if (item.type == "Income") item.total_amount else -item.total_amount
                val formattedDate = Utils.formatStringDateToDay(item.date)

                TransactionItem(
                    title = item.title,
                    amount = Utils.formatCurrency(amount),
                    icon = icon,
                    date = formattedDate,  // Display the formatted date
                    color = if (item.type == "Income") Color.Green else Color.Red
                )

                // Add a line separator after the last entry of the current month
                if (index == transactions.size - 1) {
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
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
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(51.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                ExpenseTextView(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                ExpenseTextView(
                    text = date,
                    fontSize = 13.sp,
                    color = Color.Gray // Or any light color you prefer
                )
            }
        }
        ExpenseTextView(
            text = amount,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterEnd),
            color = color
        )
    }
}

@Composable
fun CardRowItem(modifier: Modifier, title: String, amount: String, imaget: Int) {
    Column(modifier = modifier) {
        Row {
            Image(
                painter = painterResource(id = imaget),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(8.dp))
            ExpenseTextView(text = title, color = Color.White)
        }
        Spacer(modifier = Modifier.size(4.dp))
        ExpenseTextView(text = amount, color = Color.White)
    }
}


//@Composable
//fun TransactionScreen(viewModel: TransactionViewModel) {
//    // Observe the transactions from the ViewModel
//    val transactions by viewModel.transactions.observeAsState(emptyList())
//
//    // Display the transaction list
//    TransactionList(list = transactions)
//}
