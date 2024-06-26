package com.example.finalproject.domain.useCase

import com.example.finalproject.domain.entity.LoanEntity
import com.example.finalproject.domain.repository.LoanRepository
import javax.inject.Inject

class GetLoansFromRoomUseCase @Inject constructor(private val repository: LoanRepository) {
    suspend operator fun invoke(): List<LoanEntity> = repository.getLocalLoans()
}