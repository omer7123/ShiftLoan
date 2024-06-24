package com.example.finalproject.data.local.sharedPref

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocaleSharedPrefDataSourceImpl @Inject constructor(context: Context) :
    LocaleSharedPrefDataSource {
    private val sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    override suspend fun saveLocale(loc: String) {
        withContext(Dispatchers.IO) {
            sharedPref.edit().putString("locale", loc).apply()
        }
    }

    override suspend fun getLocale(): String {
        var locale: String
        withContext(Dispatchers.IO) {
            locale = sharedPref.getString("locale", "").toString()
        }
        return locale
    }
}