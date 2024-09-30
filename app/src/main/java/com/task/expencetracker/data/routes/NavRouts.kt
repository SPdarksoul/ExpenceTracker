package com.task.expencetracker.data.routes



sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Create : Screen("create")
    object Stats : Screen("stats")
    object Profile : Screen("profile")
    object Onboarding : Screen("onboarding")
    object Main : Screen("main")
}