package com.example.finalproject.domain.useCase

import com.example.finalproject.domain.repository.LoanRepository
import javax.inject.Inject

class DeleteAllLoansInRoomUseCase @Inject constructor(private val repository: LoanRepository) {

    suspend operator fun invoke(): Unit = repository.clearAllLoans()
}