package com.example.finalproject.domain.useCase

import com.example.finalproject.domain.repository.AuthenticationRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(token: String): Unit = repository.saveToken(token)
}