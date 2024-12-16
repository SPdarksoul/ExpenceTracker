package com.task.expencetracker.features.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.expencetracker.Feactures.budget.BudgetEntity
import com.task.expencetracker.Feactures.budget.BudgetViewModel

@Composable
fun BudgetScreen(viewModel: BudgetViewModel) {
    val budgets by viewModel.budgets.observeAsState(emptyList())
    var categoryInput by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Text(
            text = "Manage Monthly Budget",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input Fields
        OutlinedTextField(
            value = categoryInput,
            onValueChange = { categoryInput = it },
            label = { Text("Category") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            isError = isError && categoryInput.isBlank(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Allocated Amount") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError && amountInput.isBlank(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        // Add Budget Button
        Button(
            onClick = {
                if (categoryInput.isNotBlank() && amountInput.isNotBlank()) {
                    val allocatedAmount = amountInput.toDoubleOrNull()
                    if (allocatedAmount != null) {
                        viewModel.addBudget(
                            BudgetEntity(
                                category = categoryInput,
                                allocatedAmount = allocatedAmount,
                                spentAmount = 0.0
                            )
                        )
                        categoryInput = ""
                        amountInput = ""
                        isError = false
                    } else {
                        isError = true
                    }
                } else {
                    isError = true
                }
            },
            modifier = Modifier.align(Alignment.End),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Budget")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Budget List Section
        Text(
            text = "Your Budgets",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (budgets.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No budgets found. Start by adding one above!",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                )
            }
        } else {
            budgets.forEach { budget ->
                BudgetCard(budget)
            }
        }
    }
}


@Composable
fun BudgetCard(budget: BudgetEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = budget.category,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Allocated: $${budget.allocatedAmount}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Spent: $${budget.spentAmount}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            val progress = if (budget.allocatedAmount > 0) {
                (budget.spentAmount / budget.allocatedAmount).toFloat().coerceIn(0f, 1f)
            } else 0f

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = when {
                    progress > 1f -> Color.Red
                    progress > 0.9f -> Color.Red
                    progress > 0.6f -> Color.Yellow
                    else -> Color.Green
                },
                trackColor = Color.LightGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                progress > 1f -> {
                    Text(
                        text = "⚠️ Budget Exceeded!",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                progress > 0.9f -> {
                    Text(
                        text = "⚠️ Near Budget Limit!",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
