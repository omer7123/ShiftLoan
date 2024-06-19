package com.example.finalproject.domain.useCase

import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.LoanEntity
import com.example.finalproject.domain.repository.LoanRepository
import javax.inject.Inject

class GetLoansAllUseCase @Inject constructor(private val repository: LoanRepository) {

    suspend operator fun invoke(): Resource<List<LoanEntity>> = repository.getLoansAll()
}