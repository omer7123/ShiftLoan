package com.example.finalproject.presentation.registrationFragment

interface ValidationErrorCallback {
    fun getNameEmptyError(): String
    fun getInvalidNameError(): String
    fun getPasswordEmptyError(): String
    fun getConfirmPasswordEmptyError(): String
    fun getPasswordsDoNotMatchError(): String
}