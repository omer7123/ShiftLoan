package com.example.finalproject.presentation.loanDetailFragment

import com.example.finalproject.domain.entity.LoanEntity

sealed class LoadDetailScreenState {

    data object Initial : LoadDetailScreenState()
    data object Loading : LoadDetailScreenState()
    data class Error(val msg: String) : LoadDetailScreenState()
    data class Content(val loan: LoanEntity) : LoadDetailScreenState()
}