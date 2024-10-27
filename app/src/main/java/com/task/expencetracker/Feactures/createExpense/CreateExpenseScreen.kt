package com.task.expencetracker.features.createExpense

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.expencetracker.data.dataTransaction.TransactionAlert
import com.task.expencetracker.notification.cancelNotification
import com.task.expencetracker.notification.scheduleNotification  // Ensure this function is defined correctly.
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expencetracker.viewModel.AlertViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AlertSettingScreen(viewModel: AlertViewModel = hiltViewModel()) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf(System.currentTimeMillis()) }

    // States for showing pickers.
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { timeInMillis = dateTime }
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        ExpenseTextView(

            text = "Set Transaction Alert",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp).background(color = Color.LightGray)
        )

        // Title Input.
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Alert Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Amount Input.
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Date Picker Row.
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Date: ${dateFormatter.format(Date(dateTime))}", style = MaterialTheme.typography.bodyLarge)
            Button(onClick = { showDatePicker = true }) {
                Text("Pick Date")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Time Picker Row.
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Time: ${timeFormatter.format(Date(dateTime))}", style = MaterialTheme.typography.bodyLarge)
            Button(onClick = { showTimePicker = true }) {
                Text("Pick Time")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Date Picker Dialog.
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

        // Time Picker Dialog.
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
                true // Use 24-hour format.
            ).show()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Alert Button.
        Button(
            onClick = {
                if (title.isNotBlank() && amount.isNotBlank() && amount.toDoubleOrNull() != null && amount.toDouble() > 0) {
                    val alert = TransactionAlert(
                        title = title,
                        amount = amount.toDouble().toString(),
                        dateTime = dateTime
                    )
                    viewModel.addAlert(alert)

                    // Schedule notification with correct details.
                    scheduleNotification(context, alert)

                    // Reset fields after adding alert.
                    title = ""
                    amount = ""
                    dateTime = System.currentTimeMillis() // Reset to current time after adding alert.
                }
            },
            modifier=Modifier.fillMaxWidth()
        ) {
            Text("Add Alert")
        }

        Spacer(modifier=Modifier.height(16.dp))

        // Display Existing Alerts Header.
        Text("Existing Alerts", style=MaterialTheme.typography.titleMedium)

        Spacer(modifier=Modifier.height(8.dp))

        // Display list of alerts.
        val alertList by viewModel.alerts.collectAsState(initial=emptyList())

        if (alertList.isEmpty()) {
            Text("No alerts available", style=MaterialTheme.typography.bodyLarge)
        } else {
            Column(modifier=Modifier.padding(vertical=8.dp)) {
                alertList.forEach { alert ->
                    AlertCard(alert, viewModel, context)
                    Spacer(modifier=Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun AlertCard(alert: TransactionAlert, viewModel: AlertViewModel, context: Context) {
    val dateFormatter=SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val dateText=dateFormatter.format(Date(alert.dateTime))

    Card(
        modifier=Modifier.fillMaxWidth().padding(8.dp),
        elevation=CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier=Modifier.padding(16.dp)) {
            Text(text=alert.title, style=MaterialTheme.typography.bodyLarge)
            Spacer(modifier=Modifier.height(4.dp))
            Text(text="Amount: $${alert.amount}", style=MaterialTheme.typography.bodyMedium)
            Spacer(modifier=Modifier.height(4.dp))
            Text(text="Date: $dateText", style=MaterialTheme.typography.bodySmall)

            Spacer(modifier=Modifier.height(8.dp))

            // Settle Button to delete the alert and cancel notification.
            Button(onClick={
                viewModel.deleteAlert(alert)
                cancelNotification(context, alert)
            }) {
                Text("Settle")
            }
        }
    }
}