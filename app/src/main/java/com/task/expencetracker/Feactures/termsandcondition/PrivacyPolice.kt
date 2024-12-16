package com.task.expensetracker.features.privacy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.expencetracker.ui.theme.Zinc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Zinc)
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Welcome to Expense tracker app. This Privacy Policy outlines our practices regarding the collection, use, and disclosure of information that you may provide when using our application.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Using SectionText function for each section
                SectionText(
                    title = "1. Information We Collect",
                    content = """
                        1.1 Personal Information
                        We may collect personal information that you provide directly, such as your name, email address, and financial details (e.g., income, expenses) when you register or use our app.
                        
                        1.2 Automatically Collected Information
                        We automatically collect certain information about your device and usage of the app, including:
                        - Device type and operating system
                        - IP address
                        - Usage data (e.g., time spent in the app, features used)
                    """.trimIndent()
                )

                SectionText(
                    title = "2. How We Use Your Information",
                    content = """
                        We use the information we collect for various purposes, including:
                        - To provide and maintain our service
                        - To process your financial data and generate reports
                        - To improve our application and user experience
                        - To communicate with you about updates, promotions, or other relevant information
                    """.trimIndent()
                )

                SectionText(
                    title = "3. Information Sharing and Disclosure",
                    content = """
                        We do not sell or rent your personal information to third parties. We may share your information in the following circumstances:
                        - With your explicit consent
                        - With third-party service providers who assist us in operating our app (e.g., analytics services)
                        - As required by law or to protect our rights
                    """.trimIndent()
                )

                SectionText(
                    title = "4. Data Security",
                    content = """
                        We take reasonable measures to protect your personal information from unauthorized access, use, or disclosure. However, no method of transmission over the internet or electronic storage is completely secure.
                    """.trimIndent()
                )

                SectionText(
                    title = "5. Your Rights",
                    content = """
                        You have the right to:
                        - Access your personal data we hold
                        - Request correction of any inaccuracies in your data
                        - Request deletion of your personal data under certain circumstances
                    """.trimIndent()
                )

                SectionText(
                    title = "6. Changes to This Privacy Policy",
                    content = """
                        We may update our Privacy Policy from time to time. Any changes will be posted on this page with an updated effective date. We encourage you to review this policy periodically for any updates.
                    """.trimIndent()
                )

                SectionText(
                    title = "7. Contact Us",
                    content = """
                        If you have any questions about this Privacy Policy or our practices regarding your personal information, please contact us at [Your Contact Email].
                    """.trimIndent()
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@Composable
fun SectionText(
    title: String,
    content: String
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Zinc,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = content,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
