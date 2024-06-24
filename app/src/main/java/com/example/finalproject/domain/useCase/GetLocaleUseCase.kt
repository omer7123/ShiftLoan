package com.example.finalproject.domain.useCase

import com.example.finalproject.domain.repository.SettingsRepository
import javax.inject.Inject

class GetLocaleUseCase @Inject constructor(private val repository: SettingsRepository) {
    suspend operator fun invoke(): String = repository.getLocale()
}