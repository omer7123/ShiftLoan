package com.example.finalproject.data.remote.authentication

import com.example.finalproject.core.Resource
import com.example.finalproject.data.model.AuthModel
import com.example.finalproject.data.model.ResponseRegisterModel

interface AuthenticationDataSource {

    suspend fun login(auth: AuthModel): Resource<String>
    suspend fun registration(auth: AuthModel): Resource<ResponseRegisterModel>
}