import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Settings", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Account Management Section
        SettingsCategory(title = "Account Management") {
            SettingsMenuLink(title = "User Profile", icon = Icons.Filled.Person) { /* TODO: Navigate to User Profile */ }
            SettingsMenuLink(title = "Linked Accounts", icon = Icons.Filled.AccountBox) { /* TODO: Manage Linked Accounts */ }
        }

        // Currency Settings Section
        SettingsCategory(title = "Currency Settings") {
            SettingsMenuLink(title = "Default Currency", icon = Icons.Filled.Done) { /* TODO: Set Default Currency */ }
            SettingsCheckbox(title = "Currency Conversion", icon = Icons.Filled.List) { isChecked -> /* TODO: Handle Checkbox State */ }
        }

        // Notification Preferences Section
        SettingsCategory(title = "Notification Preferences") {
            SettingsSwitch(title = "Expense Alerts", icon = Icons.Filled.Notifications) { isChecked -> /* TODO: Handle Switch State */ }
            SettingsSwitch(title = "Transaction Notifications", icon = Icons.Filled.Notifications) { isChecked -> /* TODO: Handle Switch State */ }
        }

        // Legal Information Section
        SettingsCategory(title = "Legal Information") {
            SettingsMenuLink(title = "Terms of Service", icon = Icons.Filled.Create) { /* TODO: Show Terms of Service */ }
            SettingsMenuLink(title = "Privacy Policy", icon = Icons.Filled.Lock) { /* TODO: Show Privacy Policy */ }
        }

        // App Version Information Section
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "App Version: 1.0.0", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun SettingsCategory(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
fun SettingsMenuLink(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title)
    }
}

@Composable
fun SettingsCheckbox(title: String, icon: ImageVector, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title)
        Spacer(modifier = Modifier.weight(1f))
        Checkbox(checked = false, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingsSwitch(title: String, icon: ImageVector, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title)
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