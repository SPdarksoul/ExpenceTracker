package com.task.expencetracker.bottomNavigation


import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.task.expencetracker.R
import com.task.expencetracker.uicomponents.ExpenseTextView


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Create,
        BottomNavItem.Stats,
        BottomNavItem.Profile
    )

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                      modifier = Modifier.size(24.dp)
                    )
                },
                label = { ExpenseTextView(text = item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    // Navigate only if the route is different from the current route
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

sealed class BottomNavItem(val label: String, val icon: Int, val route: String) {
    object Home : BottomNavItem("Home", R.drawable.home, "home")
    object Search : BottomNavItem("Search", R.drawable.baseline_search_24, "search")
    object Create : BottomNavItem("Alert", R.drawable.create, "create")
    object Stats : BottomNavItem("Stats", R.drawable.stats, "stats")
    object Profile : BottomNavItem("Setting", R.drawable.setting, "profile")
}
