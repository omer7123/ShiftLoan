package com.example.finalproject.presentation.authorizationFragment

sealed class AuthorizationStatusState {

    data object Loading : AuthorizationStatusState()
    data object Success : AuthorizationStatusState()
    data class Error(val msg: String?, val code: Int?) : AuthorizationStatusState()
}