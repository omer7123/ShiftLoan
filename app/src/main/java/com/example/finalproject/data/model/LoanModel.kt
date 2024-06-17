package com.example.finalproject.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoanConditionsModel(
    val percent: Float,
    val period: Int,
    val maxAmount: Int
)
