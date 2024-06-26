package com.example.finalproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class LoanConditionsModel(
    val percent: Float,
    val period: Int,
    val maxAmount: Int
)

@Serializable
@Entity
data class LoanModel(
    val amount: Float,
    val date: String,
    val firstName: String,
    @PrimaryKey
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: String
)

@Serializable
data class LoanRequestModel(
    val amount: Float,
    val firstName: String,
    val lastName: String,
    val percent: Float,
    val period: Int,
    val phoneNumber: String,
)
