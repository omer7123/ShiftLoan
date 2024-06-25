package com.example.finalproject.presentation.registrationFragment

sealed class RegistrationStatusState {

    data class Error(val msg: String, val code: Int?) : RegistrationStatusState()
    data object Success : RegistrationStatusState()
}