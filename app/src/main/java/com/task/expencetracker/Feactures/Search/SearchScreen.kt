package com.task.expencetracker.features.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.expencetracker.Feactures.home.TransactionList

import com.task.expencetracker.R
import com.task.expencetracker.viewModel.ExpenseViewModel


@Composable
fun SearchBar(onSearchQueryChanged: (String) -> Unit, onSearch: () -> Unit) {
    var query by remember { mutableStateOf("") }

    TextField(
        value = query,
        onValueChange = {
            query = it
            onSearchQueryChanged(it)
        },
        label = { Text("Search Transactions") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24), // Replace with your custom icon resource
                contentDescription = "Search",
                modifier = Modifier.size(24.dp)
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch() // Call the onSearch lambda
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun TransactionSearchScreen(viewModel: ExpenseViewModel = hiltViewModel()) {
    // Observing the filtered expenses from the ViewModel
    val expenses by viewModel.filteredExpenses.observeAsState(emptyList())

    Column {
        // Search bar to update the search query in ViewModel
        SearchBar(
            onSearchQueryChanged = { query ->
                viewModel.updateSearchQuery(query)
            },
            onSearch = {
                // Optional: You might not need additional actions here
            }
        )

        // Transaction list displaying filtered expenses
        TransactionList(
            modifier = Modifier.fillMaxSize(),
            list = expenses,
            title = "Recent Transactions", // Update title as needed
            searchQuery = "" // Pass the search query if needed
        )
    }
}
