//package com.task.expencetracker.onBoarding
//
//import androidx.compose.foundation.pager.*
//
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.Button
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import com.task.expencetracker.uicomponents.ExpenseTextView
//import com.task.expencetracker.R
//
//@Composable
//fun OnboardingPager(
//    onGetStartedClick: () -> Unit
//) {
//    val pagerState = rememberPagerState()
//    val scope = rememberCoroutineScope()
//    val onboardingPages = listOf(
//        OnboardingPager  (
//            imageResId = R.drawable.onboarding1,
//            title = "Track Your Expenses",
//            description = "Keep a record of all your daily expenses and stay on top of your budget."
//        ),
//        OnboardingPager(
//            imageResId = R.drawable.onboarding1,
//            title = "Analyze Your Spending",
//            description = "Visualize your spending habits with easy-to-understand graphs."
//        ),
//        OnboardingPager(
//            imageResId = R.drawable.onboarding1,
//            title = "Set Goals and Save",
//            description = "Set savings goals and track your progress with ease."
//        )
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        HorizontalPager(
//            count = onboardingPages.size,
//            state = pagerState,
//            modifier = Modifier.weight(1f)
//        ) { page ->
//            OnboardingPager(page = onboardingPages[page])
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Pager Indicator
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            repeat(onboardingPages.size) { index ->
//                val color = if (pagerState.currentPage == index) Color.Black else Color.Gray
//                Box(
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .size(12.dp)
//                        .background(color = color, shape = CircleShape)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Button to Skip or Start
//        Button(
//            onClick = onGetStartedClick,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            ExpenseTextView(text = "Get Started")
//        }
//    }
//}
