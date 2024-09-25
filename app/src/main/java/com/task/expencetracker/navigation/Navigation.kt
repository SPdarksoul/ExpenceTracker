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
import com.task.expencetracker.data.routes.Screen
import com.task.expencetracker.features.createExpense.CreateExpenseScreen
import com.task.expencetracker.features.search.TransactionSearchScreen
import com.task.expencetracker.features.stats.StatsScreen
import com.task.expencetracker.viewModel.ExpenseViewModel

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Search.route) {
            val viewModel: ExpenseViewModel = hiltViewModel()
            TransactionSearchScreen(viewModel = viewModel)
        }
        composable(Screen.Create.route) { CreateExpenseScreen() }
        composable(Screen.Stats.route) { StatsScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(route = "/add_income") {
            AddExpense(navController, isIncome = true)
        }
        composable(route = "/add_exp") {
            AddExpense(navController, isIncome = false)
        }
    }
}
