package com.example.finalproject.domain.entity

data class AuthEntity(
    val name: String,
    val password: String,
)

data class ResponseRegisterEntity(
    val name: String,
)