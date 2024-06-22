package com.example.finalproject.presentation.menuFragment

sealed class LogOutStatusState {
    data class Error(val msg: String) : LogOutStatusState()
    data object Success : LogOutStatusState()
}