package com.example.finalproject.data.remote.loan

import com.example.finalproject.core.Resource
import com.example.finalproject.data.model.LoanConditionsModel
import com.example.finalproject.data.model.LoanModel

interface LoanDataSource {
    suspend fun getLoanConditions(): Resource<LoanConditionsModel>
    suspend fun getLoansAll(): Resource<List<LoanModel>>
    suspend fun getLoan(id: Int): Resource<LoanModel>
}