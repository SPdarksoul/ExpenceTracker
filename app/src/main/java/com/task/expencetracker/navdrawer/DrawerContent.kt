package com.task.expencetracker.navdrawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.task.expencetracker.R
import com.task.expencetracker.topBar.NavItem
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expensetracker.data.routes.Screen
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White )
            .padding(13.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "User Icon",
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.White)
                    .padding(start = 16.dp, end = 24.dp)
            )

            Column {
                ExpenseTextView(
                    text = "Welcome to Expense Tracker App",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                ExpenseTextView(
                    text = "Track Securely",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
        HorizontalDivider(thickness = 2.dp, color = Color.Black)

        // Drawer items
        val navItems = listOf(
            NavItem(Screen.Home.route, R.drawable.home, "Home"),
            NavItem(Screen.Search.route, R.drawable.baseline_search_24, "Search"),
            NavItem(Screen.Create.route, R.drawable.create, "Create"),
            NavItem(Screen.Stats.route, R.drawable.stats, "Stats"),
            NavItem(Screen.Profile.route, R.drawable.baseline_person_24, "Profile"),
            NavItem(Screen.Reminders.route, R.drawable.alarm, "Reminders"),
            NavItem(Screen.Sevices.route, R.drawable.category, "Categories"),
            NavItem(Screen.Budget.route, R.drawable.budgeting, "Budgeting Tools"),
            NavItem(Screen.Profile.route, R.drawable.setting, "Settings"),
            NavItem(Screen.About.route, R.drawable.about, "About Us"),
            NavItem(Screen.Conditions.route, R.drawable.termsandconditions, "Terms & Conditions"),
            NavItem(Screen.Privacy.route, R.drawable.privacy, "Privacy Policy"),
            NavItem(Screen.Sevices.route, R.drawable.assignment, "Terms of service"),
            NavItem(Screen.Permissions.route, R.drawable.agreement, "Terms of permissions"),
            NavItem(Screen.Help.route, R.drawable.helpdesk, "Help & Support"),
        )
        LazyColumn {
            item {
                navItems.forEach { item ->
                    DrawerItem(item) { route ->
                        scope.launch { drawerState.close() }
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                        }
                    }
                }
            }
        }
    }
}