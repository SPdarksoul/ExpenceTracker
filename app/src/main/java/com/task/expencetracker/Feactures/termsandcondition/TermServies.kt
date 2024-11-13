package com.task.expencetracker.Feactures.termsandcondition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.task.expencetracker.ui.theme.Zinc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsOfServiceScreen(
    onAgree: () -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terms of Service", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Zinc)
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .padding(padding)
            ) {
                Text(
                    text = "Please review the terms of service for your Expense Tracker app.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Scrollable terms content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = """
                            Welcome to Expense Tracker. By using this application, you agree to the following terms of service:
                            
                            1. **Data Privacy**: All transaction data will be stored securely.
                            2. **Usage**: This app is for personal use only.
                            3. **Permissions**: The app may request permissions to access notifications and other functionalities.
                            4. **Liability**: The app does not take responsibility for financial decisions based on the app’s data.
                            5. **Changes**: Terms of service may change at any time.

                            For the complete terms, please visit our website or contact support.

                            Thank you for choosing Expense Tracker!
                        """.trimIndent(),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Checkbox and Agree button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Zinc,
                            uncheckedColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "I agree to the terms of service",
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onAgree,
                    enabled = isChecked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Zinc,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text(text = "Agree and Continue", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTermsOfServiceScreen() {
             TermsOfServiceScreen(onAgree = { /* Handle agreement action */ })

}
