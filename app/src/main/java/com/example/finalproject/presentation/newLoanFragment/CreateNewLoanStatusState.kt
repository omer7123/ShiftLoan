package com.example.finalproject.presentation.newLoanFragment

import com.example.finalproject.domain.entity.LoanEntity

sealed class CreateNewLoanStatusState {

    data class Error(val msg: String?) : CreateNewLoanStatusState()
    data class Success(val loan: LoanEntity) : CreateNewLoanStatusState()
    data object Loading : CreateNewLoanStatusState()
}