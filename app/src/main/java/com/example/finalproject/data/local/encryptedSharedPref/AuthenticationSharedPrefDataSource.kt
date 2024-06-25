package com.example.finalproject.data.local.encryptedSharedPref

interface AuthenticationSharedPrefDataSource {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String
    suspend fun deleteToken()
}