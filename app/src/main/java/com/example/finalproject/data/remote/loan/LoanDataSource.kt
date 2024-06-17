package com.example.finalproject.data.remote.loan

import com.example.finalproject.core.Resource
import com.example.finalproject.data.model.LoanConditionsModel

interface LoanDataSource {
    suspend fun getLoanConditions(): Resource<LoanConditionsModel>
}