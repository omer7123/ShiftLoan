package com.example.finalproject.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoanConditionsModel(
    val percent: Float,
    val period: Int,
    val maxAmount: Int
)

@Serializable
data class LoanModel(
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
