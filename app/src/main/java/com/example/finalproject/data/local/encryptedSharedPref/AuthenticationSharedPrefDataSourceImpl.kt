package com.example.finalproject.data.local.encryptedSharedPref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationSharedPrefDataSourceImpl @Inject constructor(context: Context) :
    AuthenticationSharedPrefDataSource {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        FILE_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveToken(token: String) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit()
                .putString(TOKEN, token)
                .apply()
        }
    }

    override suspend fun getToken(): String {
        val token: String
        withContext(Dispatchers.IO) {
            token = sharedPreferences.getString(TOKEN, "").toString()
        }
        return token
    }

    override suspend fun deleteToken() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit()
                .putString(TOKEN, "")
                .apply()
        }
    }

    companion object {
        private const val TOKEN = "token"
        private const val FILE_NAME = "auth_pref"
    }
}