package com.example.finalproject.data.remote.interceptor

import com.example.finalproject.data.local.encryptedSharedPref.AuthenticationSharedPrefDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val authenticationSharedPrefDataSource: AuthenticationSharedPrefDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            authenticationSharedPrefDataSource.getToken()
        }
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}