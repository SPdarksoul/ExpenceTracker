package com.task.expensetracker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController

@Composable
fun OnboardingScreen(navController: NavHostController, onComplete: () -> Unit) {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(checkAllPermissionsGranted(context)) }

    val permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION)
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        permissionGranted = permissionsMap.values.all { it }
    }

    if (permissionGranted) {
        onComplete()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("We need location permissions to continue using the app.")
            Button(onClick = { permissionLauncher.launch(permissions.toTypedArray()) }) {
                Text("Grant Permissions")
            }
        }
    }
}

// Function to check if all required permissions are granted
fun checkAllPermissionsGranted(context: Context): Boolean {
    val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    return requiredPermissions.all { permission ->
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}
