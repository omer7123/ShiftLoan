package com.example.finalproject.data.repository

import com.example.finalproject.data.local.sharedPref.LocaleSharedPrefDataSource
import com.example.finalproject.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val dataSource: LocaleSharedPrefDataSource) :
    SettingsRepository {
    override suspend fun saveLoacale(locale: String) {
        dataSource.saveLocale(locale)
    }

    override suspend fun getLocale(): String {
        return dataSource.getLocale()
    }
}