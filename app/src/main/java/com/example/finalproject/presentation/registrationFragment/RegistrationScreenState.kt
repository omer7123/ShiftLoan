package com.example.finalproject.presentation.registrationFragment

sealed class RegistrationScreenState {
    data object Initial : RegistrationScreenState()
    data object Loading : RegistrationScreenState()
    data class ValidationError(
        val nameError: String? = null,
        val passwordError: String? = null,
        val confirmPasswordError: String? = null
    ) : RegistrationScreenState()

    data object Success : RegistrationScreenState()
    data class Error(val msg: String) : RegistrationScreenState()
}