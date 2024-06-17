package com.example.finalproject.data.remote.loan

import com.example.finalproject.core.BaseDataSource
import com.example.finalproject.core.Resource
import com.example.finalproject.data.model.LoanConditionsModel
import javax.inject.Inject

class LoanDataSourceImpl @Inject constructor(private val loanService: LoanService) : LoanDataSource,
    BaseDataSource() {
    override suspend fun getLoanConditions(): Resource<LoanConditionsModel> = getResult {
        loanService.getLoanConditions()
    }
}