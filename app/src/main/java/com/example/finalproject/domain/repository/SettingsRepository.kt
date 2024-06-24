package com.example.finalproject.domain.repository

interface SettingsRepository {

    suspend fun saveLoacale(locale: String)
    suspend fun getLocale(): String
}