//package com.task.expencetracker.features.budget
//
//import com.task.expencetracker.Feactures.budget.BudgetEntity
//
//fun calculateSuggestedBudget(expenses: List<BudgetEntity>, income: Double): Map<String, Double> {
//    // Group expenses by category and calculate total spent per category
//    val categoryTotals = expenses.groupBy { it.type }.mapValues { entry ->
//        entry.value.sumOf { it.total_amount }
//    }
//
//    // Calculate total expenses
//    val totalExpenses = categoryTotals.values.sum()
//
//    // Handle edge case: If total expenses are zero, return an empty budget
//    if (totalExpenses == 0.0 || income <= 0) {
//        return emptyMap()
//    }
//
//    // Calculate allocatable income after savings (e.g., 20% for savings)
//    val savingsPercentage = 0.2 // 20% savings
//    val allocatableIncome = income * (1 - savingsPercentage)
//
//    // Calculate budget suggestions per category based on historical spending proportions
//    val budgetSuggestions = mutableMapOf<String, Double>()
//    categoryTotals.forEach { (category, totalSpent) ->
//        // Calculate the budget allocation for each category
//        val allocation = (totalSpent / totalExpenses) * allocatableIncome
//        budgetSuggestions[category] = allocation
//    }
//
//    return budgetSuggestions
//}
