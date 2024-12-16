package com.task.expencetracker.features.create

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.task.expencetracker.R
import com.task.expencetracker.ui.theme.Zinc
import com.task.expencetracker.uicomponents.ExpenseTextView

@Composable
fun CreateScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFE4FAE8)), // Soft pastel green background
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExpenseTextView(
            text = "Select Transaction Type",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Zinc
            ),
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        TransactionCard(
            title = "Add Expense",
            description = "Track your expenses seamlessly and efficiently.",
            details = "Categorize expenses, add notes, and schedule reminders.",
            backgroundColor = listOf(Color(0xFFEF9A9A), Color(0xFFE57373)), // Soft red gradient
            iconResId = R.drawable.ic_expense,
            onClick = { navController.navigate("/add_exp") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TransactionCard(
            title = "Add Income",
            description = "Keep a record of your income sources.",
            details = "Include salary, freelance work, or passive earnings.",
            backgroundColor = listOf(Color(0xFF81C784), Color(0xFF4CAF50)), // Soft green gradient
            iconResId = R.drawable.ic_income,
            onClick = { navController.navigate("/add_income") }
        )
    }
}

@Composable
fun TransactionCard(
    title: String,
    description: String,
    details: String,
    backgroundColor: List<Color>,
    iconResId: Int,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    // Animate background color on click
    val cardColor by animateColorAsState(
        targetValue = if (isPressed) Color(0xFFDCE775) else Color.Transparent
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 12.dp)
            .clickable {
                isPressed = true
                onClick()
                isPressed = false
            }
            .background(cardColor, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(backgroundColor))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Text(
                    text = details,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateScreen() {
    CreateScreen(navController = NavHostController(LocalContext.current))
}
