package com.task.expensetracker.data.routes

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Create : Screen("create")
    object Stats : Screen("stats")
    object Profile : Screen("profile")
    object Reminders : Screen("reminders") // Added Reminders screen
    object Categories : Screen("categories") // Added Categories screen
    object Budget : Screen("budget") // Added Budgeting Tools screen
    object Settings : Screen("settings") // Added Settings screen
    object About : Screen("about")
    object Help : Screen("help") // Added Help & Support screen
    object Logout : Screen("logout") // Added Logout screen
    object Onboarding : Screen("onboarding")
    object Main : Screen("main")
}
