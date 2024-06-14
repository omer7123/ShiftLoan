package com.example.finalproject.domain.useCase

import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(auth: AuthEntity) = authenticationRepository.login(auth)
}