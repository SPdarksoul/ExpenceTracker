package com.task.expencetracker.Feactures.termsandcondition

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.task.expencetracker.ui.theme.Zinc
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expensetracker.data.routes.Screen


@Composable
fun TermsPermissions(navController: NavHostController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Header
        Text(
            text = "Why We Need Permissions",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.background(Zinc).padding(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Explanation for each permission
        PermissionCard(
            title = "Location",
            description = "We use your location to help find nearby services and provide location-based recommendations."
        )

        PermissionCard(
            title = "Notifications",
            description = "We send notifications to alert you of new expenses or reminders, ensuring you stay updated."
        )

        PermissionCard(
            title = "SMS and Read SMS",
            description = "We read your SMS to automatically track transactions and manage your expenses without manual entry."
        )

        PermissionCard(
            title = "Exact Alarm",
            description = "We use this permission to send reminders at exact times for due payments and other critical alerts."
        )

        PermissionCard(
            title = "Wake Lock & Boot Completed",
            description = "These permissions ensure our reminders work properly even if your phone is restarted."
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Button to proceed to the onboarding screen
        Button(
            onClick = { navController.navigate(Screen.Onboarding.route) },
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Zinc
            )
        ) {
            ExpenseTextView("Continue")
        }
    }
}

@Composable
fun PermissionCard(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExpenseTextView(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Zinc
            )
            ExpenseTextView(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
