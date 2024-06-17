package com.example.finalproject.domain.repository

import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.LoanConditionsEntity

interface LoanRepository {
    suspend fun getLoanConditions(): Resource<LoanConditionsEntity>
}