package com.task.expencetracker.features.stats

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.task.expencetracker.ui.theme.Zinc
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expencetracker.viewModel.StatsViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(viewModel: StatsViewModel = hiltViewModel()) {
    val currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
    var selectedMonth by remember { mutableStateOf(currentMonth) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Load data when selectedMonth changes
    LaunchedEffect(selectedMonth) {
        isLoading = true
        errorMessage = null
        viewModel.loadExpensesForMonth(selectedMonth)

        viewModel.entries.collect { entries ->
            isLoading = false
            errorMessage = if (entries.isEmpty()) {
                "No data available for $selectedMonth"
            } else null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { ExpenseTextView("Monthly Statistics", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Zinc)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Show loading or error message
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                errorMessage?.let {
                    ExpenseTextView(text = it, color = Color.Black, modifier = Modifier.padding(16.dp))
                } ?: run {
                    // Display chart if data exists
                    val entries by viewModel.entries.collectAsState()
                    val chartEntries = viewModel.getEntriesForChart(entries)
                    if (chartEntries.isNotEmpty()) {
                        LineChartView(expenses = chartEntries)
                    } else {
                        ExpenseTextView("No valid data available for $selectedMonth")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons to switch months
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MonthButton("Current Month") { selectedMonth = currentMonth }
                MonthButton("Previous Month") {
                    selectedMonth = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun MonthButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        ExpenseTextView(text = label)
    }
}

@Composable
fun LineChartView(expenses: List<Entry>) {
    val context = LocalContext.current

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250)
                setBackgroundColor(android.graphics.Color.WHITE)
                axisLeft.textColor = android.graphics.Color.BLACK
                axisRight.isEnabled = false // Disable right axis for simplicity
                legend.isEnabled = false
            }
        },
        update = { lineChart ->
            if (expenses.isNotEmpty()) {
                val dataSet = LineDataSet(expenses, "Expenses").apply {
                    color = Color.Blue.toArgb()
                    lineWidth = 2f
                    setDrawValues(false) // Hide value labels
                    setDrawFilled(true)
                    fillColor = Color.Blue.toArgb()
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }

                lineChart.xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return (value.toInt() + 1).toString() // Simple formatter (day 1, 2, 3...)
                    }
                }

                lineChart.data = LineData(dataSet)
                lineChart.invalidate()
            } else {
                lineChart.clear()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}
