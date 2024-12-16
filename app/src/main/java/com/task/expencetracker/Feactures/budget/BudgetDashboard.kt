package com.task.expencetracker.Feactures.budget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp

@Composable
fun BudgetDashboard(budgets: List<BudgetEntity>) {
    Column {
        budgets.forEach { budget ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(budget.category, modifier = Modifier.weight(1f))
                LinearProgressIndicator(
                    progress = (budget.spentAmount / budget.allocatedAmount).toFloat(),
                    modifier = Modifier.weight(3f)
                )
            }
        }
    }
}
