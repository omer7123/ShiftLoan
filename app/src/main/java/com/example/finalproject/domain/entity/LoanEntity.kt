package com.example.finalproject.domain.entity

data class LoanConditionsEntity(
    val percent: Float,
    val period: Int,
    val maxAmount: Int
)

data class LoanEntity(
    val amount: Float,
    val date: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: String
)
