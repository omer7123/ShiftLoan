package com.example.finalproject.domain.entity

import java.io.Serializable

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

data class LoanRequestWithoutUserData(
    val amount: Int,
    val percent: Float,
    val period: Int
) : Serializable

data class LoanRequestEntity(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Float,
    val period: Int,
    val phoneNumber: String,
)