package com.example.finalproject.data.local.encryptedSharedPref

import com.example.finalproject.data.model.AuthModel

interface AuthenticationSharedPrefDataSource {
    suspend fun saveAuth(name: String, password: String)
    suspend fun getAuth(): AuthModel
}