package com.task.expencetracker.navigation


import AddExpense
import ProfileScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.task.expencetracker.Feactures.home.HomeScreen
import com.task.expencetracker.authentictaion.AuthScreen

import com.task.expencetracker.data.routes.Screen
import com.task.expencetracker.features.createExpense.CreateExpenseScreen
import com.task.expencetracker.features.search.TransactionSearchScreen
import com.task.expencetracker.features.stats.StatsScreen

import com.task.expencetracker.viewModel.ExpenseViewModel
import com.task.expencetracker.viewmodel.AuthViewModel
import com.task.expensetracker.MainScreen


@Composable
fun Navigation(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(navController, startDestination = "auth") {
        composable("auth") { AuthScreen(navController, authViewModel) }
        composable(route = Screen.Main.route) {
            MainScreen()
        }

        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Search.route) {
            val viewModel: ExpenseViewModel = hiltViewModel()
            TransactionSearchScreen(viewModel = viewModel)
        }
        composable(Screen.Create.route) { CreateExpenseScreen() }
        composable(Screen.Stats.route) { StatsScreen(navController) }

        composable(Screen.Profile.route) {
            // Fetch user data (you can fetch from ViewModel or hardcode for now)
            val userName = "John Doe" // Replace with actual data from ViewModel or repository
            val userEmail = "johndoe@email.com" // Replace with actual data
            val profilePictureUrl: String? = null // Replace with actual image URL if available

            // Pass the required parameters to ProfileScreen
            ProfileScreen(
                userName = userName,
                userEmail = userEmail,
                profilePictureUrl = profilePictureUrl,
                onLogoutClick = {
                    // Handle logout action here
                },
                onEditProfileClick = {
                    // Handle profile edit action here
                },
                onImageSelected = { uri ->
                    // Handle image selection here
                }
            )
        }

        composable(route = "/add_income") {
            AddExpense(navController, isIncome = true)
        }
        composable(route = "/add_exp") {
            AddExpense(navController, isIncome = false)
        }
    }
}
