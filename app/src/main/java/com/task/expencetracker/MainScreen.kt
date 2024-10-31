package com.task.expensetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.task.expencetracker.R
import com.task.expencetracker.bottomNavigation.BottomNavigationBar
import com.task.expencetracker.navigation.Navigation
import com.task.expencetracker.topBar.NavItem
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expensetracker.data.routes.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Enhanced Drawer Content
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background( Color(0xFF2F7E79))
                    .padding(13.dp)
            ) {
                // Drawer header with user info
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "User Icon",
                        modifier = Modifier
                            .size(70.dp)
                            .background( Color(0xFF2F7E79))
                            .padding( start = 16.dp, end = 24.dp)

                    )

                    Column  {
                        ExpenseTextView(
                            text = "Welcome to Expense Tracker App",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )

                        ExpenseTextView(
                            text = "Track Securely",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,


                        )}
                }
                Divider(color = Color.Black, thickness = 2.dp)

                // Enhanced drawer items
                val navItems = listOf(
                    NavItem(Screen.Home.route, R.drawable.home, "Home"),
                    NavItem(Screen.Search.route, R.drawable.baseline_search_24, "Search"),
                    NavItem(Screen.Create.route, R.drawable.create, "Create"),
                    NavItem(Screen.Stats.route, R.drawable.stats, "Stats"),
                    NavItem(Screen.Profile.route, R.drawable.baseline_person_24, "Profile"),
                    NavItem(Screen.Reminders.route, R.drawable.alarm, "Reminders"),
                    NavItem(Screen.Categories.route, R.drawable.category, "Categories"),
                    NavItem(Screen.Budget.route, R.drawable.budgeting, "Budgeting Tools"),
                    NavItem(Screen.Settings.route, R.drawable.setting, "Settings"),
                    NavItem(Screen.About.route, R.drawable.about, "About Us"),
                    NavItem(Screen.Conditions.route, R.drawable.termsandconditions, "Terms & Conditions"),
                    NavItem(Screen.Privacy.route, R.drawable.privacy, "Privacy Policy"),
                    NavItem(Screen.Sevices.route, R.drawable.assignment, "Terms of service"),
                    NavItem(Screen.Permissions.route, R.drawable.agreement, "terms of permissions"),
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
                        }}}
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { ExpenseTextView(text = "Expense Tracker") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(navController = navController)
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Navigation(navController)
                }
            }
        )
    }
}

@Composable
fun DrawerItem(item: NavItem, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item.route) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.label,

            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        ExpenseTextView(text = item.label, style = MaterialTheme.typography.bodyLarge)
    }
}