package com.example.finalproject.domain.useCase

import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.domain.repository.AuthenticationRepository
import javax.inject.Inject

class SaveSharedPrefAuthUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    suspend operator fun invoke(auth: AuthEntity): Unit = repository.saveAuth(auth)
}