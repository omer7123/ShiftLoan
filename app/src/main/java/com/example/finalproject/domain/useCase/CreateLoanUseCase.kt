package com.example.finalproject.domain.useCase

import com.example.finalproject.domain.entity.LoanRequestEntity
import com.example.finalproject.domain.repository.LoanRepository
import javax.inject.Inject

class CreateLoanUseCase @Inject constructor(private val repository: LoanRepository) {

    suspend operator fun invoke(loan: LoanRequestEntity) = repository.createLoan(loan)
}