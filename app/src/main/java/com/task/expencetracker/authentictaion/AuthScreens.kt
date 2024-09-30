// AuthScreen.kt
package com.task.expencetracker.authentictaion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.task.expencetracker.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSignUp by remember { mutableStateOf(true) }
    var showMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Dialog for error messages
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Error") },
            text = { Text(showMessage) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            coroutineScope.launch {
                if (isSignUp) {
                    val success = authViewModel.signUp(email, password)
                    if (success) {
                        navController.navigate("home") // Navigate to home screen after signup
                    } else {
                        showMessage = "Sign Up Failed. Please try again."
                        showDialog = true
                    }
                } else {
                    val success = authViewModel.login(email, password)
                    if (success) {
                        navController.navigate("home") // Navigate to home screen after login
                    } else {
                        showMessage = "Login Failed. Please check your credentials."
                        showDialog = true
                    }
                }
            }
        }) {
            Text(if (isSignUp) "Sign Up" else "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { isSignUp = !isSignUp }) {
            Text(if (isSignUp) "Already have an account? Login" else "Don't have an account? Sign Up")
        }

        if (showMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = showMessage)
        }
    }
}