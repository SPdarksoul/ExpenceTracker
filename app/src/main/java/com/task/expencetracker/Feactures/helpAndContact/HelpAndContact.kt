package com.task.expencetracker.features.helpAndContact
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.task.expencetracker.R
import com.task.expencetracker.ui.theme.Zinc
import com.task.expencetracker.uicomponents.ExpenseTextView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpAndContact() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { ExpenseTextView("Help & Support", color = Zinc) },
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .padding(padding)
            ) {
                SearchBar()
                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(sections) { section ->
                        HelpSectionCard(section = section)
                    }
                }
            }
        }
    )
}
@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Search for Help", color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray)
        }
    )
}


@Composable
fun HelpSectionCard(section: HelpSection) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { /* Navigate to Section Details */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Zinc),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = section.icon),
                contentDescription = section.title,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExpenseTextView(
                text = section.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 14.sp)
            )
        }
    }
}

data class HelpSection(val title: String, val icon: Int)

val sections = listOf(
    HelpSection("FAQs", R.drawable.baseline_question_answer_24),
    HelpSection("Contact Us", R.drawable.baseline_question_answer_24),
    HelpSection("User Guide", R.drawable.baseline_question_answer_24),
    HelpSection("Feedback", R.drawable.baseline_question_answer_24),
    HelpSection("Live Chat", R.drawable.baseline_question_answer_24),
    HelpSection("Community Forum", R.drawable.baseline_question_answer_24),
    HelpSection("Troubleshooting", R.drawable.baseline_question_answer_24),
    HelpSection("Report a Bug", R.drawable.baseline_question_answer_24),
    HelpSection("App Updates", R.drawable.baseline_question_answer_24),
    HelpSection("Privacy Policy", R.drawable.baseline_question_answer_24),
    HelpSection("Terms of Service", R.drawable.baseline_question_answer_24)
)

@Preview(showBackground = true)
@Composable
fun PreviewHelpAndContact() {
    HelpAndContact()
}
