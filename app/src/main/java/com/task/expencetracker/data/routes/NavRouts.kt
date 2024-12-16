package com.task.expensetracker.data.routes

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Hello : Screen("hello")
    object Search : Screen("search")
    object Create : Screen("create")
    object Stats : Screen("stats")
    object Profile : Screen("profile")
    object Reminders : Screen("reminders")
    object Categories : Screen("categories")
    object Budget : Screen("budget")
    object Settings : Screen("settings")
    object About : Screen("about")
    object Help : Screen("help")
    object Logout : Screen("logout")
    object Onboarding : Screen("onboarding")
    object Main : Screen("main")
    object Privacy : Screen("privacy")
    object Sevices : Screen("service")
    object Conditions : Screen("conditions")
    object Bugeting : Screen("bugeting")
    object Permissions : Screen("permission")
}