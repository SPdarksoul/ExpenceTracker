package com.task.expensetracker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.task.expencetracker.R
import com.task.expencetracker.bottomNavigation.BottomNavigationBar
import com.task.expencetracker.data.routes.Screen
import com.task.expencetracker.navigation.Navigation
import com.task.expencetracker.topBar.DrawerContent
import com.task.expencetracker.topBar.NavItem

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Get AuthViewModel using viewModel() and passing AuthViewModelFactory


    // State to manage loading and refreshing
    var isRefreshing by remember { mutableStateOf(false) }

    // Function to simulate data refresh
    val refreshData: suspend () -> Unit = {
        isRefreshing = true
        delay(2000) // Simulate network call or data loading
        isRefreshing = false
    }

    // Gesture detection for pull down
    var offsetY by remember { mutableStateOf(0f) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController,
                items = listOf(
                    NavItem(route = Screen.Home.route, icon = R.drawable.home, label = "Home"),
                    NavItem(route = Screen.Search.route, icon = R.drawable.baseline_search_24, label = "Search"),
                    NavItem(route = Screen.Create.route, icon = R.drawable.create, label = "Create"),
                    NavItem(route = Screen.Stats.route, icon = R.drawable.stats, label = "Stats"),
                    NavItem(route = Screen.Profile.route, icon = R.drawable.baseline_person_24, label = "Profile")
                )
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Expense Tracker") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
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
                        .pointerInput(Unit) {
                            detectVerticalDragGestures { change, dragAmount ->
                                offsetY += dragAmount
                                if (offsetY > 100 && !isRefreshing) {
                                    scope.launch { refreshData() }
                                    offsetY = 0f
                                }
                                change.consume()
                            }
                        }
                ) {

                    // Navigation Composable with AuthViewModel passed in as a parameter.
                    Navigation(navController)

                    // Custom refresh indicator (CircularProgressIndicator)
                    if (isRefreshing) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 16.dp)
                        )
                    }
                }
            }
        )
    }
}