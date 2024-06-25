package com.example.finalproject.presentation.homeAuthenticationFragment

sealed class HomeAuthenticationAuthStatus {

    data object Loading : HomeAuthenticationAuthStatus()
    data object Success : HomeAuthenticationAuthStatus()
    data object Error : HomeAuthenticationAuthStatus()
}