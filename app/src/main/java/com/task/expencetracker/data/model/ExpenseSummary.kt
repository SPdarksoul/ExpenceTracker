package com.task.expencetracker.data.model

data class ExpenseSummary(
    val type: String,
    val date: String,
    val total_amount: Double
)

fun mapEntityToSummary(entity: ExpenceEntity): ExpenseSummary {
    return ExpenseSummary(
        date = entity.date,
        total_amount = entity.total_amount,
        type = entity.type // Assuming ExpenseSummary has a 'type' field
    )
}
fun mapSummaryToEntity(summary: ExpenseSummary): ExpenceEntity {
    return ExpenceEntity(
        id = 0, // Assuming an auto-generated or placeholder ID
        title = "Expense", // Placeholder title or extract from summary if available
        total_amount = summary.total_amount,
        date = summary.date,
        type = summary.type
    )
}
