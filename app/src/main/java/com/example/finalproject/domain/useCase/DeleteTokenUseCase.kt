package com.example.finalproject.domain.useCase

import com.example.finalproject.domain.repository.AuthenticationRepository
import javax.inject.Inject

class DeleteTokenUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    suspend operator fun invoke(): Unit = repository.deleteToken()
}