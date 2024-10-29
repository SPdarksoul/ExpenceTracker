package com.task.expencetracker.features.createExpense

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.expencetracker.data.dataTransaction.TransactionAlert
import com.task.expencetracker.notification.cancelNotification
import com.task.expencetracker.notification.scheduleNotification
import com.task.expencetracker.ui.theme.Zinc
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expencetracker.viewModel.AlertViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
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
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFE3F2FD)) // Light blue background
    ) {
        EnhancedExpenseTextView(text = "Set Transaction Alert")

        // Title Input with Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Card elevation
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Alert Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = outlinedTextFieldColors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }



    // Amount Input with Card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Card elevation
    ) {
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = outlinedTextFieldColors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
        Row {
            // Date Picker Row.
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                ExpenseTextView(
                    "Date: ${dateFormatter.format(Date(dateTime))}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Button(
                    onClick = { showDatePicker = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Zinc),
                    modifier = Modifier.padding(8.dp)
                ) {
                    ExpenseTextView("Pick Date", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.padding(24.dp))
            // Time Picker Row.
            Column(
modifier = Modifier.padding(8.dp)
            ) {
                ExpenseTextView(
                    "Time: ${timeFormatter.format(Date(dateTime))}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Button(
                    onClick = { showTimePicker = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Zinc),
                    modifier = Modifier.padding(8.dp)
                ) {
                    ExpenseTextView("Pick Time", color = Color.White)
                }
            }

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
        }
            // Add Alert Button with Ripple Effect
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
                        dateTime =
                            System.currentTimeMillis() // Reset to current time after adding alert.
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Zinc) // Zinc color
            ) {
                ExpenseTextView("Add Alert", color = Color.White)
            }

        // Display Existing Alerts Header.
        EnhancedExpenseTextView(text = "Existing Alerts")

        Spacer(modifier = Modifier.height(8.dp))

        // Display list of alerts.
        val alertList by viewModel.alerts.collectAsState(initial = emptyList())

        if (alertList.isEmpty()) {
            ExpenseTextView("No alerts available", style = MaterialTheme.typography.bodyLarge)
        } else {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                alertList.forEach { alert ->
                    AlertCard(alert, viewModel, context)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun AlertCard(alert: TransactionAlert, viewModel: AlertViewModel, context: Context) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val dateText = dateFormatter.format(Date(alert.dateTime))

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ExpenseTextView(text = alert.title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            ExpenseTextView(text = "Amount: $${alert.amount}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            ExpenseTextView(text = "Date: $dateText", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp) // Divider for separation

            CustomButton(
                onClick = {
                    viewModel.deleteAlert(alert)
                    cancelNotification(context, alert)
                },
                text = "Settle"
            )
        }
    }
}

@Composable
fun CustomButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Zinc)
    ) {
        ExpenseTextView(text = text, color = Color.White)
    }
}
@Composable
fun EnhancedExpenseTextView(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF2F7E79), Color(0xFF25A969)) // Gradient colors
                ),
                shape = RoundedCornerShape(8.dp) // Rounded corners
            )
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp)) // Border color and shape
            .shadow(4.dp, RoundedCornerShape(8.dp)) // Shadow effect
            .padding(16.dp)
            .size(14.dp)
    ) {
        ExpenseTextView(
            text = text,
            modifier = Modifier.align(Alignment.Center), color = Color.White,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )

        )
    }
}
