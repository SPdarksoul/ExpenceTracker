package com.task.expensetracker.navigation

import AddExpense
import SettingsScreen
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.task.expencetracker.Feactures.budgettingTool
import com.task.expencetracker.Feactures.home.HomeScreen
import com.task.expencetracker.Feactures.termsandcondition.TermsOfServiceScreen
import com.task.expencetracker.Feactures.termsandcondition.TermsPermissions
import com.task.expencetracker.Feactures.termsandcondition.Termscondition
import com.task.expencetracker.MainContent
import com.task.expencetracker.features.about.AboutUsScreen
import com.task.expencetracker.features.createExpense.AlertSettingScreen
import com.task.expencetracker.features.helpAndContact.HelpAndContact
import com.task.expencetracker.features.search.TransactionSearchScreen
import com.task.expencetracker.features.stats.StatsScreen
import com.task.expencetracker.viewModel.AlertViewModel
import com.task.expencetracker.viewModel.ExpenseViewModel
import com.task.expensetracker.OnboardingScreen
import com.task.expensetracker.checkAllPermissionsGranted
import com.task.expensetracker.data.routes.Screen
import com.task.expensetracker.features.privacy.PrivacyPolicyScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    // State variables for onboarding and permissions
    var isNewUser by remember { mutableStateOf(sharedPreferences.getBoolean("isNewUser", true)) }
    var allPermissionsGranted by remember { mutableStateOf(checkAllPermissionsGranted(context)) }

    NavHost(
        navController = navController,
        startDestination = if (isNewUser || !allPermissionsGranted) Screen.Onboarding.route else Screen.Home.route
    ) {
        // Onboarding Screen
        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController) {
                // Mark onboarding as completed and check permissions
                sharedPreferences.edit().putBoolean("isNewUser", false).apply()
                isNewUser = false
                allPermissionsGranted = checkAllPermissionsGranted(context)

                // Navigate to the main screen if permissions are granted
                if (allPermissionsGranted) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            }
        }

        // Main content screens
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Hello.route) {
            MainContent(navController = navController)
        }

        composable(Screen.Search.route) {
            val viewModel: ExpenseViewModel = hiltViewModel()
            TransactionSearchScreen(viewModel = viewModel)
        }

        composable(Screen.Create.route) {
            val alertViewModel: AlertViewModel = hiltViewModel()
            AlertSettingScreen(viewModel = alertViewModel)
        }

        composable(Screen.Stats.route) {
            StatsScreen()
        }

        composable(Screen.About.route) {
            AboutUsScreen()
        }

        composable(Screen.Profile.route) {
            SettingsScreen(navController)
        }

        composable(Screen.Bugeting.route) {
            budgettingTool()
        }

        composable(Screen.Help.route) {
            HelpAndContact()
        }

        composable(Screen.Sevices.route) {
            TermsOfServiceScreen {
                navController.navigate(Screen.Home.route)
            }
        }

        composable(Screen.Conditions.route) {
            Termscondition()
        }

        composable(Screen.Permissions.route) {
            TermsPermissions()
        }

        composable(Screen.Privacy.route) {
            PrivacyPolicyScreen()
        }

        // Additional routes for adding expenses
        composable("/add_income") {
            AddExpense(navController = navController, isIncome = true)
        }

        composable("/add_exp") {
            AddExpense(navController = navController, isIncome = false)
        }
    }
}
