package com.example.finalproject.presentation.loansFragment

import com.example.finalproject.domain.entity.LoanEntity

sealed class LoansScreenState {

    data object Initial : LoansScreenState()
    data object Loading : LoansScreenState()
    data class Content(val list: List<LoanEntity>) : LoansScreenState()
    data class Error(val msg: String) : LoansScreenState()
}