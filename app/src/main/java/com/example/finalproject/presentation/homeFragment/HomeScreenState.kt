package com.example.finalproject.presentation.homeFragment

import com.example.finalproject.domain.entity.LoanConditionsEntity

sealed class HomeScreenState {

    data object Initial : HomeScreenState()
    data class Error(val msg: String) : HomeScreenState()
    data class Content(
        val validationMsg: String = "",
        val sumLoan: String,
        val list: List<String>? = null,
        val conditions: LoanConditionsEntity
    ) : HomeScreenState()
}