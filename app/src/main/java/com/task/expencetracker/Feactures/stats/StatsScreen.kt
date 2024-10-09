package com.task.expencetracker.features.stats

import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.task.expencetracker.R
import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.data.util.Utils
import com.task.expencetracker.features.transactionScreen.TransactionList
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expencetracker.viewModel.StatsViewModel

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun StatsScreen(navController: NavController, viewModel: StatsViewModel = hiltViewModel()) {
    // Get the current month in "yyyy-MM" format and previous month.
    val currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
    val previousMonth = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"))

    // State to manage selected month (current or previous)
    var selectedMonth by remember { mutableStateOf(currentMonth) }

    // Load data for the current month by default when the screen is first displayed.
    LaunchedEffect(Unit) {
        viewModel.loadExpensesForMonth(selectedMonth)
    }

    // Collect entries from ViewModel state flow.
    val entries by viewModel.entries.collectAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                ExpenseTextView(
                    text = "Statistics",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            // Display the chart for the selected month's data.
            if (entries.isNotEmpty()) {
                LineChartView(expenses = viewModel.getEntriesForChart(entries))
            } else {
                Text(text = "No data available for $selectedMonth", modifier = Modifier.padding(16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to switch between current and previous month.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        selectedMonth = currentMonth
                        viewModel.loadExpensesForMonth(currentMonth)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Current Month")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        selectedMonth = previousMonth
                        viewModel.loadExpensesForMonth(previousMonth)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Previous Month")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            val expenses by viewModel.getSortedExpenses().collectAsState(initial = emptyList())
          TransactionList(list= expenses)
        }
    }
}


@Composable
fun LineChartView(expenses: List<Entry>) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            LayoutInflater.from(context).inflate(R.layout.stats_line_chart, null).apply {
                findViewById<LineChart>(R.id.lineChart).apply {
                    // Set up chart properties here if needed (initialization can be done here)
                }
            }
        },
        modifier = Modifier.fillMaxWidth().height(250.dp)
    ) { view ->
        val lineChart = view.findViewById<LineChart>(R.id.lineChart)

        val dataSet = LineDataSet(expenses, "Expenses & Income").apply {
            color = android.graphics.Color.parseColor("#FF2F7E79")
            valueTextColor = android.graphics.Color.BLACK
            lineWidth = 3f
            axisDependency = YAxis.AxisDependency.RIGHT
            setDrawFilled(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            valueTextSize = 12f

            val drawable = ContextCompat.getDrawable(context, R.drawable.char_gradient)
            drawable?.let { fillDrawable = it }
        }

        lineChart.xAxis.valueFormatter =
            object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return Utils.formatDateForChart(value.toLong()) // Format x-axis as dates.
                }
            }

        lineChart.data = LineData(dataSet)
        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.setDrawAxisLine(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.invalidate() // Refresh the chart with new data.
    }
}