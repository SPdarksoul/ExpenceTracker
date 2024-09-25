package com.task.expencetracker.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.task.expencetracker.uicomponents.ExpenseTextView

data class NavItem(
    val route: String,
    val icon: Int,
    val label: String
)
@Composable
fun DrawerContent(
    navController: NavController,
    items: List<NavItem>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ExpenseTextView("Navigation Drawer", modifier = Modifier.padding(16.dp))
        Divider()

        val currentRoute = navController.currentBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationDrawerItem(
                selected = currentRoute == item.route,
                onClick = {
                    // Navigate to the selected route
                    navController.navigate(item.route) {
                        // Ensure the selected route replaces the current route
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    ExpenseTextView(text = item.label)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}