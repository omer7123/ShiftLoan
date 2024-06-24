package com.example.finalproject.domain.useCase

import com.example.finalproject.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveLocaleUseCase @Inject constructor(private val repository: SettingsRepository) {
    suspend operator fun invoke(locale: String): Unit = repository.saveLoacale(locale)
}