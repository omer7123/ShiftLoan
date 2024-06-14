package com.example.finalproject.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthModel(
    val name: String,
    val password: String,
)

@Serializable
data class ResponseRegisterModel(
    val name: String,
    val role: String,
)
