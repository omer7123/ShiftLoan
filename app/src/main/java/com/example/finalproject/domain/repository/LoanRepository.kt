package com.example.finalproject.domain.repository

import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.LoanConditionsEntity
import com.example.finalproject.domain.entity.LoanEntity

interface LoanRepository {
    suspend fun getLoanConditions(): Resource<LoanConditionsEntity>
    suspend fun getLoansAll(): Resource<List<LoanEntity>>
    suspend fun getLoan(id: Int): Resource<LoanEntity>
}