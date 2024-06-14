package com.example.finalproject.domain.useCase

import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.domain.entity.ResponseRegisterEntity
import com.example.finalproject.domain.repository.AuthenticationRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(auth: AuthEntity): Resource<ResponseRegisterEntity> =
        repository.registration(auth)
}