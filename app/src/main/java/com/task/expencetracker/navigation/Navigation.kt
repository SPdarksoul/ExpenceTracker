package com.task.expencetracker.navigation


import AddExpense
import androidx.compose.runtime.Composable
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
import com.task.expensetracker.MainScreen
import com.task.expensetracker.features.profile.CategoriesScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
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
        composable(Screen.Profile.route) { CategoriesScreen(onCategoryClick = { category ->}) }



        composable(route = "/add_income") {
            AddExpense(navController, isIncome = true)
        }
        composable(route = "/add_exp") {
            AddExpense(navController, isIncome = false)
        }
    }
}
