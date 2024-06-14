package com.example.finalproject.data.local.encryptedSharedPref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.finalproject.data.model.AuthModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationSharedPrefDataSourceImpl @Inject constructor(context: Context) :
    AuthenticationSharedPrefDataSource {


    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "auth_pref",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveAuth(name: String, password: String) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit()
                .putString(NAME, name)
                .apply()

            sharedPreferences.edit()
                .putString(PASSWORD, password)
                .apply()
        }
    }

    override suspend fun getAuth(): AuthModel {
        val name: String
        val password: String
        withContext(Dispatchers.IO) {
            name = sharedPreferences.getString(NAME, "").toString()
            password = sharedPreferences.getString(PASSWORD, "").toString()
        }
        return AuthModel(name, password)
    }

    companion object {
        private const val NAME = "name"
        private const val PASSWORD = "password"
    }
}