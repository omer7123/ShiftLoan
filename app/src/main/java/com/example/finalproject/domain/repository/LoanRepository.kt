package com.example.finalproject.domain.repository

import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.LoanConditionsEntity
import com.example.finalproject.domain.entity.LoanEntity
import com.example.finalproject.domain.entity.LoanRequestEntity

interface LoanRepository {
    suspend fun getLoanConditions(): Resource<LoanConditionsEntity>
    suspend fun getLoansAll(): Resource<List<LoanEntity>>
    suspend fun getLoan(id: Int): Resource<LoanEntity>
    suspend fun createLoan(loan: LoanRequestEntity): Resource<LoanEntity>
    suspend fun saveLoans(loans: List<LoanEntity>)
    suspend fun getLocalLoans(): List<LoanEntity>
    suspend fun clearAllLoans()
}