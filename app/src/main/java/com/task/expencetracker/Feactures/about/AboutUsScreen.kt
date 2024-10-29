package com.task.expencetracker.features.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.task.expencetracker.R
import com.task.expencetracker.uicomponents.ExpenseTextView

@Composable
fun AboutUsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.expense),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Section Title
            ExpenseTextView(
                text = "About Us - Expense Tracker App",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                color = Color(0xFF2F7E79)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // About Sections
            ExpandableSection("Who We Are", """
                We are a team of passionate developers and financial enthusiasts with a vision to make personal finance management simpler and more accessible. Our journey started with the realization that keeping track of daily expenses and planning for long-term financial goals shouldn’t be a tedious task. 
                We envisioned a tool that not only makes this process seamless but also helps people build better financial habits, save more money, and plan for a secure future.
            """.trimIndent())

            Spacer(modifier = Modifier.height(16.dp))

            ExpandableSection("Our Commitment", """
                We are committed to continuously improving the Expense Tracker App, adding more features, enhancing the user experience, and ensuring that managing finances becomes an empowering activity for everyone.
            """.trimIndent())

            Spacer(modifier = Modifier.height(16.dp))

            ExpandableSection("Get Started Today!", """
                Take the first step toward financial freedom by downloading the Expense Tracker App.
            """.trimIndent())

            Spacer(modifier = Modifier.height(24.dp))
            // Features Section
            SectionTitle("Key Features")

            Spacer(modifier = Modifier.height(8.dp))

            FeatureList()
            Spacer(modifier = Modifier.height(24.dp))
            // New Features Header
            ExpenseTextView(
                text = "With our app, you get:",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF2F7E79)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // New Features List
            FeatureItem("Real-time Expense Tracking", "Log your expenses effortlessly and get a real-time view of your financial status. Whether it's a small coffee or a monthly utility bill, every transaction is recorded instantly, ensuring that you never lose track of your spending.")
            FeatureItem("Custom Categories", "Create and manage categories based on your unique spending habits. Whether it’s groceries, transportation, entertainment, or savings, our flexible category system ensures your finances are organized the way you like.")
            FeatureItem("Budgeting Tools", "Set budgets for different categories and receive alerts when you're close to exceeding them. This feature allows you to maintain financial discipline and stick to your financial goals without compromising on essentials.")
            FeatureItem("Detailed Insights and Analytics", "Get insightful charts and graphs showing where your money is going. With this, you can see trends, identify spending patterns, and make informed decisions to optimize your spending habits.")
            FeatureItem("Automatic Payment Recording", "Our app listens to transaction notifications on your phone and automatically logs these expenses, making it easier for you to keep track without any manual input. The automation ensures accuracy and saves time.")
            FeatureItem("Reminder and Alert System", "Never miss a payment or important bill again! The app allows you to set reminders for bills, subscriptions, or recurring expenses, and alerts you before the due date so you’re always prepared.")
            FeatureItem("Data Security and Privacy", "We understand that your financial data is sensitive. That’s why we use advanced encryption techniques to ensure your data is safe and secure. You have full control over your data, and we prioritize user privacy above everything else.")

            Spacer(modifier = Modifier.height(24.dp))


            Spacer(modifier = Modifier.height(16.dp))

            // Team Section
            SectionTitle("Meet the Team")
            TeamList()

            Spacer(modifier = Modifier.height(16.dp))

            // Get Started Section
            SectionTitle("Get Started Today!")
            SectionDescription(
                "Take the first step toward financial freedom by downloading the Expense Tracker App..."
            )

            Spacer(modifier = Modifier.height(16.dp))
// Contact Information
            ContactSection()
        }
    }
}
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold),
        color = Color(0xFF2F7E79),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun SectionDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Gray,
        textAlign = TextAlign.Justify,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun FeatureItem(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF2F7E79))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ContactSection() {
    ExpenseTextView(
        text = "For more information, contact us at:",
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
        color = Color(0xFF2F7E79),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    )

    ExpenseTextView(
        text = "sagarpal1545@gmail.com",
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
@Composable
fun ExpandableSection(title: String, text: String) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Title
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF2F7E79)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Text Content
        Text(
            text = if (isExpanded) text else text.take(100) + "...",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
            textAlign = TextAlign.Justify
        )

        // Toggle Button
        TextButton(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = if (isExpanded) "Read Less" else "Read More",
                color = Color(0xFF2F7E79),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun FeatureList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF8F8F8), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        listOf(
            "✔️ Easy Transaction Entry" to "Our intuitive interface makes entering transactions simple. Quickly add details such as title, amount, category, and date.",
            "✔️ Multi-Currency Support" to "Track expenses in multiple currencies, perfect for travelers and multi-currency management.",
            "✔️ Recurring Expenses" to "Automate regular expenses to save time with recurring entries.",
            "✔️ Expense Filtering and Search" to "Filter expenses by category, date, or amount to find transactions quickly.",
            "✔️ Expense Reports" to "Generate reports summarizing your finances over any selected period.",
            "✔️ Push Notifications for Overspending" to "Receive alerts when nearing or exceeding a set budget.",
            "✔️ Expense Sharing" to "Share expense data with family or friends, and create shared reports."
        ).forEachIndexed { index, (title, description) ->
            FeatureItem(title, description)
            if (index != 6) Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun TeamList() {
    val teamMembers = listOf(
        "Sagar - Lead Developer",
        "Sagar - UI/UX Designer",
        "Sagar - Project Manager"
    )
    teamMembers.forEach { member ->
        TeamMember(member)
    }
}

@Composable
fun TeamMember(member: String) {
    Text(
        text = member,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFF0F8F8), shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        textAlign = TextAlign.Center
    )
}