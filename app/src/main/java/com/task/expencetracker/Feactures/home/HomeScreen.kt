package com.task.expencetracker.Feactures.home


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.task.expencetracker.R
import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.data.util.Utils
import com.task.expencetracker.ui.theme.Zinc
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expencetracker.viewModel.HomeViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar, add) = createRefs()

            // Top bar and other UI elements
            Image(painter = painterResource(id = R.drawable.ic_topbar), contentDescription = null, modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Column(modifier = Modifier.align(Alignment.CenterStart)) {
                    ExpenseTextView(text = "Welcome Back", color = Color.White)
                    ExpenseTextView(text = "Let's Check Your Expense", color = Color.White)
                }
                Image(painter = painterResource(id = R.drawable.ic_notification), contentDescription = null, modifier = Modifier.align(Alignment.CenterEnd))
            }

            // Collect values from ViewModel
            val expenses by viewModel.getSortedExpenses().collectAsState(initial = emptyList())
            var expense by remember { mutableStateOf("0.00") }
            var income by remember { mutableStateOf("0.00") }
            var balance by remember { mutableStateOf("0.00") }

            LaunchedEffect(Unit) {
                viewModel.getTotalExpense { total ->
                    expense = total
                }
                viewModel.getTotalIncome { total ->
                    income = total
                }
                viewModel.getBalance { bal ->
                    balance = bal
                }
            }

            CardItem(
                modifier = Modifier.constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                balance = balance, income = income, expense = expense
            )
            TransactionList(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(list) {
                        top.linkTo(card.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    },
                list = expenses
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(add) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.BottomEnd
            ) {
                MultiFloatingActionButton(modifier = Modifier, navController = navController)
            }
        }
    }
}

@Composable
fun MultiFloatingActionButton(modifier: Modifier, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Secondary FABs
            AnimatedVisibility(visible = expanded) {
                Column(horizontalAlignment = Alignment.End) {
                    SmallFloatingActionButton(
                        onClick = { navController.navigate("/add_income") },
                        modifier = Modifier.size(48.dp),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_income),
                            contentDescription = "Edit"
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    SmallFloatingActionButton(
                        onClick = { navController.navigate("/add_exp") },
                        modifier = Modifier.size(48.dp),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_expense),
                            contentDescription = "Edit"
                        )

                    }
                }
            }

            // Main FAB
            SmallFloatingActionButton(
                onClick = {
                    expanded = !expanded
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(56.dp)

            ) {
                Icon(Icons.Filled.Add, "Small floating action button.")
            }
        }
    }
}

@Composable
fun CardItem(
    modifier: Modifier,
    balance: String, income: String, expense: String
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Zinc)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column {
                ExpenseTextView(
                    text = "Total Balance",

                    color = Color.White
                )
                Spacer(modifier = Modifier.size(8.dp))
                ExpenseTextView(
                    text = balance, color = Color.White,
                )
            }
            Image(
                painter = painterResource(id = R.drawable.dots_menu),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CardRowItem(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                title = "Income",
                amount = income,
                imaget = R.drawable.ic_income
            )
            Spacer(modifier = Modifier.size(8.dp))
            CardRowItem(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                title = "Expense",
                amount = expense,
                imaget = R.drawable.ic_expense
            )
        }

    }
}


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
    println("Current Month: $currentMonth") // Debugging

    // Filter transactions based on search query and selected month
    val filteredList = list.filter {
        val formattedDate = Utils.formatStringDateToMonthYear(it.date)
        println("Filtering date: ${it.date}, formattedDate: $formattedDate") // Debugging

        val matchesSearchQuery = it.title.contains(searchQuery, ignoreCase = true) ||
                it.type.contains(searchQuery, ignoreCase = true) ||
                formattedDate.contains(searchQuery, ignoreCase = true)

        // Filter based on selected month if provided
        val matchesSelectedMonth = selectedMonth?.let { month ->
            formattedDate == month
        } ?: true

        matchesSearchQuery && matchesSelectedMonth
    }

    println("Filtered List Size: ${filteredList.size}") // Debugging

    // Sort transactions by date in descending order (latest first)
    val sortedList = filteredList.sortedByDescending {
        try {
            LocalDate.parse(it.date, Utils.inputFormatter)
        } catch (e: DateTimeParseException) {
            println("Invalid date found: ${it.date} - ${e.message}")
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
                        Text(
                            text = "See all",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clickable { /* Handle click action */ },
                            fontSize = 16.sp,
                            color = Color.Blue
                        )
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
                println("Displaying date: ${item.date}, formattedDate: $formattedDate") // Debugging

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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}