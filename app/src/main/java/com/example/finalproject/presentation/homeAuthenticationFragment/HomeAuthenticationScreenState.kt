package com.example.finalproject.presentation.homeAuthenticationFragment

sealed class HomeAuthenticationScreenState {

    data object Initial : HomeAuthenticationScreenState()
    data object Loading : HomeAuthenticationScreenState()
    data object Success : HomeAuthenticationScreenState()
    data class Error(val msg: String) : HomeAuthenticationScreenState()
}