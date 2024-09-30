//package com.task.expencetracker.onBoarding
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import com.task.expencetracker.uicomponents.ExpenseTextView
//
//@Composable
//fun OnboardingContent(pageData: OnboardingPageData) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Image(painter = painterResource(id = pageData.imageResId), contentDescription = null)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        ExpenseTextView(text = pageData.title, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        ExpenseTextView(text = pageData.description, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
//    }
//}