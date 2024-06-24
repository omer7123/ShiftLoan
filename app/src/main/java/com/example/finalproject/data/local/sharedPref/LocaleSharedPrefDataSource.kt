package com.example.finalproject.data.local.sharedPref

interface LocaleSharedPrefDataSource {
    suspend fun saveLocale(loc: String)
    suspend fun getLocale(): String
}