package com.task.expencetracker.Feactures.termsandcondition

import android.content.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.task.expencetracker.ui.theme.Zinc
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expensetracker.data.routes.Screen


@Composable
fun Termscondition(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    var isAccepted by remember { mutableStateOf(false) }
    var hasAcceptedBefore by remember {
        mutableStateOf(sharedPreferences.getBoolean("termsAccepted", false))
    }

    // If already accepted, navigate to Home screen
    if (hasAcceptedBefore) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Conditions.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        ExpenseTextView(
            text = "Terms and Conditions",
            style = MaterialTheme.typography.headlineMedium,
            color = Zinc
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable section for Terms and Conditions text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            TermsText()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox to accept the terms
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isAccepted,
                onCheckedChange = { isAccepted = it }
            )
            Text(
                text = "I agree to the Terms and Conditions",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to accept the terms
        Button(
            onClick = {
                sharedPreferences.edit().putBoolean("termsAccepted", true).apply()
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Conditions.route) { inclusive = true }
                }
            },
            enabled = isAccepted,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            ExpenseTextView("Accept and Continue")
        }
    }
}

@Composable
fun TermsText() {
    val termsContent = """
        Welcome to My Go Tour!

        Please read the following Terms and Conditions carefully before using this application.
        
        1. **Acceptance of Terms**: By using this app, you agree to be bound by these terms.
        2. **Privacy**: We respect your privacy and are committed to protecting your personal information.
        3. **Permissions**: The app requires permissions to access your location, notifications, and SMS for providing core features.
        4. **Limitation of Liability**: We are not liable for any damages resulting from the use of this app.
        5. **Changes to Terms**: We may update these terms at any time. Continued use of the app constitutes acceptance of the updated terms.
        6. **Termination**: We reserve the right to terminate access to the app if you violate these terms.
        
        If you have any questions, please contact us at support@mygotour.com.
        
        Thank you for using My Go Tour!
    """.trimIndent()

    // Scrollable text section
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = termsContent,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
