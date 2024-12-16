package com.task.expensetracker.features.categories

import AddExpense
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.task.expencetracker.R
import com.task.expencetracker.ui.theme.Zinc
import com.task.expensetracker.data.routes.Screen

// Data model for categories
data class Category(
    val name: String,
    val iconRes: Int,
    val color: Color
)

// Sample category list with icons from drawable resources
val categoriesList = listOf(
    Category("Food", R.drawable.baseline_fastfood_24, Color(0xFFFFCDD2)),
    Category("Shopping", R.drawable.baseline_shopping_cart_24, Color(0xFFBBDEFB)),
    Category("Travel", R.drawable.baseline_flight_24, Color(0xFFB2DFDB)),
    Category("Entertainment", R.drawable.baseline_local_movies_24, Color(0xFFFFF9C4)),
    Category("Health", R.drawable.baseline_favorite_24, Color(0xFFE1BEE7)),
    Category("Bills", R.drawable.agreement, Color(0xFFD1C4E9)),
    Category("Transport", R.drawable.directioncar, Color(0xFFC8E6C9)),
    Category("Utilities", R.drawable.baseline_lightbulb_24, Color(0xFFFFF176)),
    Category("Fitness", R.drawable.about, Color(0xFF80CBC4)),
    Category("Rent", R.drawable.home, Color(0xFFAED581)),
    Category("Savings", R.drawable.money, Color(0xFFCE93D8))
)

// Main Categories Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Zinc,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(paddingValues)
//                .background(MaterialTheme.colorScheme.background)g
        ) {
            items(categoriesList) { category ->
                CategoryCard(category = category) {
                    navController.navigate("/add_exp") // Navigate to AddExpense screen
                }
            }
        }
    }
}

// Composable to display each category as a card
@Composable
fun CategoryCard(category: Category, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(120.dp)
            .clickable { onClick(

            ) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = category.color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

//// Category Detail Screen (Optional)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CategoryDetailScreen(categoryName: String?) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Details for $categoryName") },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    titleContentColor = Color.White
//                )
//            )
//        }
//    ) {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Text(
//                text = "Details for $categoryName",
//                style = MaterialTheme.typography.headlineSmall
//            )
//        }
//    }
//}
