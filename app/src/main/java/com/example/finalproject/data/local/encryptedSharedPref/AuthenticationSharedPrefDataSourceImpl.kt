package com.example.finalproject.data.local.encryptedSharedPref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.finalproject.data.model.AuthModel
import javax.inject.Inject

class AuthenticationSharedPrefDataSourceImpl @Inject constructor(context: Context) :
    AuthenticationSharedPrefDataSource {


    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    val sharedPreferences = EncryptedSharedPreferences.create(
        "auth_pref",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveAuth(name: String, password: String) {
        sharedPreferences.edit()
            .putString(NAME, name)
            .apply()

        sharedPreferences.edit()
            .putString(PASSWORD, password)
            .apply()
    }

    override suspend fun getAuth(): AuthModel {
        val name = sharedPreferences.getString(NAME, "")
        val password = sharedPreferences.getString(PASSWORD, "")
        return AuthModel(name.toString(), password.toString())
    }

    companion object {
        private const val NAME = "name"
        private const val PASSWORD = "password"
    }
}