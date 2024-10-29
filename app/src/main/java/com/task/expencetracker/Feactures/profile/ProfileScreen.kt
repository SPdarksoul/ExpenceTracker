import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.task.expencetracker.R
import com.task.expencetracker.ui.theme.Zinc
import com.task.expencetracker.uicomponents.ExpenseTextView


@Composable
fun SettingsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)

    ) {
        // Title

        item {
            Text(text = "Settings", style = MaterialTheme.typography.headlineLarge, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Account Management Section
        item {
            SettingsCategory(title = "Account Management") {
                SettingsMenuLink(
                    title = "User Profile",
                    icon = Icons.Filled.Person
                ) { /* TODO: Navigate to User Profile */ }
                SettingsMenuLink(
                    title = "Linked Accounts",
                    icon = Icons.Filled.AccountBox
                ) { /* TODO: Manage Linked Accounts */ }
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
                    icon = Icons.Filled.List
                ) { isChecked -> /* TODO: Handle Checkbox State */ }
            }
        }

        // Notification Preferences Section
        item {
            SettingsCategory(title = "Notification Preferences") {
                SettingsSwitch(
                    title = "Expense Alerts",
                    icon = Icons.Filled.Notifications
                ) { isChecked -> /* TODO: Handle Switch State */ }
                SettingsSwitch(
                    title = "Transaction Notifications",
                    icon = Icons.Filled.Notifications
                ) { isChecked -> /* TODO: Handle Switch State */ }
            }
        }

        // Legal Information Section
        item {
            SettingsCategory(title = "Legal Information") {
                SettingsMenuLink(
                    title = "Terms of Service",
                    icon = Icons.Filled.Create
                ) { /* TODO: Show Terms of Service */ }
                SettingsMenuLink(
                    title = "Privacy Policy",
                    icon = Icons.Filled.Lock
                ) { /* TODO: Show Privacy Policy */ }
            }
        }

        // App Version Information Section
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "App Version: 1.0.0", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp)) // Optional spacing at the bottom
        }
    }
}

@Composable
fun SettingsCategory(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding( 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Zinc)
            .padding(16.dp)
//            .shadow(1.dp, MaterialTheme.shapes.medium) // Add shadow for depth
    ) {
        ExpenseTextView(text = title, style = MaterialTheme.typography.titleMedium,color = Color.Black)
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
//        colors = CardDefaults.cardColors(containerColor = Zinc), // Highlight color
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Elevation for depth
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null,)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        }
    }
}

@Composable
fun SettingsCheckbox(title: String, icon: ImageVector, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding( 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        ExpenseTextView(text = title,  color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
        Checkbox(checked = false, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingsSwitch(title: String, icon: ImageVector, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding( 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = false, onCheckedChange = onCheckedChange)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    MaterialTheme {
        SettingsScreen()
    }
}
