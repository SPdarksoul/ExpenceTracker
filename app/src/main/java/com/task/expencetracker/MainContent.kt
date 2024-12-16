package com.task.expencetracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.task.expencetracker.bottomNavigation.BottomNavigationBar
import com.task.expencetracker.navdrawer.DrawerContent
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expensetracker.navigation.Navigation
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainContent(navController: NavHostController = rememberNavController()) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController, drawerState = drawerState)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { ExpenseTextView("Expense Tracker", fontSize = 20.sp) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Icon")
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            // Ensuring the navigation is correctly shown between the top and bottom bars
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // Pass the navController to the Navigation composable
                Navigation(navController) // Ensure the navController is passed correctly
            }
        }
    }
}
