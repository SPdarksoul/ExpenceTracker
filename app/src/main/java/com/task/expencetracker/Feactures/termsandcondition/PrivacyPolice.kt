package com.task.expencetracker.Feactures.termsandcondition


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
            fun PrivacyPolice(navController: NavController) {
                // Sample Privacy Policy Text
                val privacyPolicyText = """
        Privacy Policy

        Effective Date: [Insert Date]

        At My Go Tour, we respect your privacy and are committed to protecting your personal information. 
        This privacy policy outlines how we collect, use, and safeguard your information.

        Information We Collect:
        - Personal Information: When you create an account, we collect personal details such as your name, email address, and profile picture.
        - Usage Data: We may collect information on how you access and use our app.

        How We Use Your Information:
        - To provide and maintain our services
        - To notify you about changes to our services
        - To allow you to participate in interactive features of our service when you choose to do so
        - To provide customer support
        - To gather analysis or valuable information so that we can improve our services

        Data Security:
        We implement a variety of security measures to maintain the safety of your personal information.

        Changes to This Privacy Policy:
        We may update our Privacy Policy from time to time. We will notify you of any changes by posting the new Privacy Policy on this page.

        Contact Us:
        If you have any questions about this Privacy Policy, please contact us at support@expensetracker.com.
    """.trimIndent()

                // Main Layout with Scroll Support
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(Color(0xFFF5F5F5)) // Light gray background for better contrast
                        .verticalScroll(rememberScrollState()), // Enable vertical scrolling
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Header
                    Text(
                        text = "Privacy Policy",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C3E50) // Darker color for better readability
                    )

                    // Privacy Policy Content
                    Text(
                        text = privacyPolicyText,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = Color(0xFF34495E) // Slightly lighter than header for contrast
                    )

                    // Back Button
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 16.dp)
                            .fillMaxWidth(0.3f), // Slightly smaller width
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2F7E79), // Attractive button color
                            contentColor = Color.White // White text on button
                        )
                    ) {
                        Text(text = "Back")
                    }
                }
            }
