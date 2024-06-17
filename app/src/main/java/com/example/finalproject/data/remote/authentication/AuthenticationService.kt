package com.example.finalproject.data.remote.authentication

import com.example.finalproject.data.model.AuthModel
import com.example.finalproject.data.model.ResponseRegisterModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/login")
    suspend fun login(@Body auth: AuthModel): Response<String>

    @POST("/registration")
    suspend fun registration(@Body auth: AuthModel): Response<ResponseRegisterModel>
}