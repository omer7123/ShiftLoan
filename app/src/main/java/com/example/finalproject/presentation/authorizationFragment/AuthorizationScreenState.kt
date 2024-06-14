package com.example.finalproject.presentation.authorizationFragment

sealed class AuthorizationScreenState {

    data object Initial : AuthorizationScreenState()
    data object Loading : AuthorizationScreenState()
    data object Success : AuthorizationScreenState()
    data class ValidationError(
        val nameError: String? = null,
        val passwordError: String? = null,
    ) : AuthorizationScreenState()

    data class Error(val msg: String) : AuthorizationScreenState()
}