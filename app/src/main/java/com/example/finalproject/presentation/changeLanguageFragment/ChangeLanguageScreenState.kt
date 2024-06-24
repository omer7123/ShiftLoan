package com.example.finalproject.presentation.changeLanguageFragment

sealed class ChangeLanguageScreenState {

    data object Initial : ChangeLanguageScreenState()
    data class Error(val msg: String) : ChangeLanguageScreenState()
    data class Content(val locate: String) : ChangeLanguageScreenState()
}