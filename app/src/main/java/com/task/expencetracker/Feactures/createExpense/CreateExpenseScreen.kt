package com.task.expencetracker.features.createExpense

import AlertViewModel
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.task.expencetracker.data.dataTransaction.TransactionAlert
import com.task.expencetracker.uicomponents.ExpenseTextView
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AlertSettingScreen(viewModel: AlertViewModel) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var dateTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    // States for showing pickers
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { timeInMillis = dateTime }
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Set Transaction Alert",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Title Input
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Alert Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Amount Input
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Date Picker Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Date: ${dateFormatter.format(Date(dateTime))}", style = MaterialTheme.typography.bodyLarge)
            Button(onClick = { showDatePicker = true }) {
                Text("Pick Date")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Time Picker Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Time: ${timeFormatter.format(Date(dateTime))}", style = MaterialTheme.typography.bodyLarge)
            Button(onClick = { showTimePicker = true }) {
                Text("Pick Time")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Date Picker Dialog
        if (showDatePicker) {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    dateTime = calendar.timeInMillis
                    showDatePicker = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Time Picker Dialog
        if (showTimePicker) {
            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    dateTime = calendar.timeInMillis
                    showTimePicker = false
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true // Use 24-hour format
            ).show()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Alert Button
        Button(
            onClick = {
                if (title.isNotBlank() && amount.isNotBlank() && amount.toDoubleOrNull() != null && amount.toDouble() > 0) {
                    viewModel.addAlert(TransactionAlert(
                        title,
                        amount.toDouble().toString(), // Assuming TransactionAlert expects Double for amount.
                        dateTime))
                    title = ""
                    amount = ""
                    dateTime = System.currentTimeMillis() // Reset to current time after adding alert.
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Alert")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display Existing Alerts Header
        Text("Existing Alerts", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        // Display list of alerts using LaunchedEffect to collect flow.
        LaunchedEffect(Unit) {
            viewModel.alerts.collectLatest { alerts ->
                if (alerts.isEmpty()) {
                    ExpenseTextView(
                        "No alerts available", style = MaterialTheme.typography.bodyMedium)
                } else {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        alerts.forEach { alert ->
                            AlertCard(alert)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlertCard(alert: TransactionAlert) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val dateText = dateFormatter.format(Date(alert.dateTime))

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = alert.title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Amount: $${alert.amount}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Date: $dateText", style = MaterialTheme.typography.bodySmall)
        }
    }
}