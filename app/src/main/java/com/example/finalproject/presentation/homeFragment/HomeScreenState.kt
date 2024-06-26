package com.example.finalproject.presentation.homeFragment

import com.example.finalproject.domain.entity.LoanConditionsEntity
import com.example.finalproject.domain.entity.LoanEntity

sealed class HomeScreenState {

    data object Initial : HomeScreenState()
    data object Loading : HomeScreenState()
    data class Error(val msg: String) : HomeScreenState()
    data class Content(
        val sumLoan: String,
        val list: List<LoanEntity>?,
        val conditions: LoanConditionsEntity?
    ) : HomeScreenState()
}