package com.task.expensetracker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.task.expencetracker.uicomponents.ExpenseTextView
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun OnboardingScreen(navController: NavHostController, onComplete: () -> Unit) {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(checkAllPermissionsGranted(context)) }
    var notificationAccessGranted by remember { mutableStateOf(isNotificationAccessEnabled(context)) }

    // List of all required permissions
    val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.SCHEDULE_EXACT_ALARM,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_BOOT_COMPLETED,
        Manifest.permission.WAKE_LOCK
    )

    // Launcher for requesting multiple permissions
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        permissionGranted = permissionsMap.values.all { it }
    }

    // Automatically navigate to Home if all permissions are granted
    LaunchedEffect(permissionGranted, notificationAccessGranted) {
        if (permissionGranted && notificationAccessGranted) {
            onComplete()
        }
    }

    // Onboarding screen content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ExpenseTextView(
            text = "Permissions Required",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card for requesting permissions
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExpenseTextView(
                    text = "We need access to your location, notifications, and SMS to provide a seamless experience.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { permissionLauncher.launch(permissions.toTypedArray()) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    ExpenseTextView("Grant App Permissions")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card for notification access
        if (!notificationAccessGranted) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ExpenseTextView(
                        text = "We also need notification access to automatically track your expenses.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { openNotificationAccessSettings(context) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        ExpenseTextView("Grant Notification Access")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExpenseTextView(
                        text = "Please allow notification access to enable expense tracking.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ExpenseTextView(
                        text = "Please  re-open the app after the Allowing  All the permissions .",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// Function to check if all required permissions are granted
@RequiresApi(Build.VERSION_CODES.S)
fun checkAllPermissionsGranted(context: Context): Boolean {
    val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.SCHEDULE_EXACT_ALARM,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_BOOT_COMPLETED,
        Manifest.permission.WAKE_LOCK
    )

    return requiredPermissions.all { permission ->
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}

// Function to check if Notification Access is enabled
fun isNotificationAccessEnabled(context: Context): Boolean {
    val enabledPackages = NotificationManagerCompat.getEnabledListenerPackages(context)
    return enabledPackages.contains(context.packageName)
}

// Function to open Notification Access settings
fun openNotificationAccessSettings(context: Context) {
    val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
    context.startActivity(intent)
}
