import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.task.expencetracker.ui.theme.Zinc
import com.task.expencetracker.uicomponents.ExpenseTextView

@Composable
fun SettingsScreen(navController: NavController) {
    // States for notifications
    var expenseAlertsEnabled by remember { mutableStateOf(false) }
    var transactionNotificationsEnabled by remember { mutableStateOf(false) }
    var currencyConversionEnabled by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Title
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Account Management Section
        item {
            SettingsCategory(title = "Usage ") {
                SettingsMenuLink(
                    title = "About Us",
                    icon = Icons.Filled.Person
                ) {  navController.navigate("about") }
                SettingsMenuLink(
                    title = "Help & Support",
                    icon = Icons.Filled.AccountBox
                ) {  navController.navigate("help") }
            }
        }

        // Currency Settings Section
        item {
            SettingsCategory(title = "Currency Settings") {
                SettingsMenuLink(
                    title = "Default Currency",
                    icon = Icons.Filled.Done
                ) { /* TODO: Set Default Currency */ }
                SettingsCheckbox(
                    title = "Currency Conversion",
                    icon = Icons.Filled.List,
                    isChecked = currencyConversionEnabled,
                    onCheckedChange = { isChecked ->
                        currencyConversionEnabled = isChecked
                        // Handle Currency Conversion state change
                    }
                )
            }
        }

        // Notification Preferences Section
        item {
            SettingsCategory(title = "Notification Preferences") {
                SettingsSwitch(
                    title = "Expense Alerts",
                    icon = Icons.Filled.Notifications,
                    isChecked = expenseAlertsEnabled,
                    onCheckedChange = { isChecked ->
                        expenseAlertsEnabled = isChecked
                        // Handle Expense Alerts state change
                        if (isChecked) {
                            // Enable Expense Alerts
                        } else {
                            // Disable Expense Alerts
                        }
                    }
                )
                SettingsSwitch(
                    title = "Transaction Notifications",
                    icon = Icons.Filled.Notifications,
                    isChecked = transactionNotificationsEnabled,
                    onCheckedChange = { isChecked ->
                        transactionNotificationsEnabled = isChecked
                        // Handle Transaction Notifications state change
                        if (isChecked) {
                            // Enable Transaction Notifications
                        } else {
                            // Disable Transaction Notifications
                        }
                    }
                )
            }
        }

        // Legal Information Section
        item {
            SettingsCategory(title = "Legal Information") {
                SettingsMenuLink(
                    title = "Terms of Service",
                    icon = Icons.Filled.Create
                ) { navController.navigate("service") }
                SettingsMenuLink(
                    title = "Privacy Policy",
                    icon = Icons.Filled.Lock,
                ) { navController.navigate("privacy") }
            }
        }

        // App Version Information Section
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "App Version: 1.0.0",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp)) // Optional spacing at the bottom
        }
    }
}

@Composable
fun SettingsCategory(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Zinc)
            .padding(16.dp)
    ) {
        ExpenseTextView(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
fun SettingsMenuLink(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(onClick = onClick)
            .padding(12.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        }
    }
}

@Composable
fun SettingsCheckbox(
    title: String,
    icon: ImageVector,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        ExpenseTextView(text = title, color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingsSwitch(
    title: String,
    icon: ImageVector,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        ExpenseTextView(text = title, color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black, // Thumb color when checked
                uncheckedThumbColor = Color.White, // Thumb color when unchecked
                checkedTrackColor = Color.Black.copy(alpha = 0.5f), // Track color when checked
                uncheckedTrackColor = Color.White.copy(alpha = 0.5f) // Track color when unchecked
            )
        )
    }
}
