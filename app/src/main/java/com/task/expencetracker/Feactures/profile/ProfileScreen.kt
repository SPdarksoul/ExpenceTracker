package com.task.expensetracker.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CategoriesScreen(onCategoryClick: (Category) -> Unit) {
    val categories = listOf(
        Category("Food & Dining", Icons.Default.Favorite, Color(0xFFEC407A)),
        Category("Shopping", Icons.Default.ShoppingCart, Color(0xFFAB47BC)),
        Category("Transport", Icons.Default.Favorite, Color(0xFF42A5F5)),
        Category("Entertainment", Icons.Default.Favorite, Color(0xFF26A69A)),
        Category("Health", Icons.Default.Favorite, Color(0xFFEF5350)),
        Category("Utilities", Icons.Default.Favorite, Color(0xFFFF7043)),
        Category("Travel", Icons.Default.Favorite, Color(0xFF66BB6A)),
        Category("Others", Icons.Default.Favorite, Color(0xFF8D6E63))
    )

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Cyan).padding(16.dp)
    ) {
        Text(
            text = "Expense Categories",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4), // Set to 4 columns in a row
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category) } // Pass click event to card
                            )
                        }
                    }
                    Divider(color = Color.Gray, thickness = 1.dp) // Add divider after each row of cards.
                }
            }

@Composable
fun CategoryCard(category: Category, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick), // Make the card clickable
        colors = CardDefaults.cardColors(
            containerColor = category.color.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Align items to start horizontally
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                tint = category.color,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.padding(start = 16.dp)) // Space between icon and text

            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                color = category.color,
                modifier = Modifier.weight(1f) // Allow text to take available space if needed
            )
        }
    }
}

data class Category(
    val name: String,
    val icon: ImageVector,
    val color: Color
)