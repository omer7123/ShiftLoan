package com.example.finalproject.domain.useCase

import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.LoanConditionsEntity
import com.example.finalproject.domain.repository.LoanRepository
import javax.inject.Inject

class GetLoanConditionsUseCase @Inject constructor(private val repository: LoanRepository) {
    suspend operator fun invoke(): Resource<LoanConditionsEntity> = repository.getLoanConditions()
}