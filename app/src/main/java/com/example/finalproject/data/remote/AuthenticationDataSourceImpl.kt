package com.example.finalproject.data.remote

import com.example.finalproject.core.BaseDataSource
import com.example.finalproject.core.Resource
import com.example.finalproject.data.model.AuthModel
import com.example.finalproject.data.model.ResponseRegisterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationDataSourceImpl @Inject constructor(private val authService: AuthenticationService) :
    AuthenticationDataSource, BaseDataSource() {
    override suspend fun login(auth: AuthModel): Resource<String> = getResult {
        withContext(Dispatchers.IO) {
            authService.login(auth)
        }
    }

    override suspend fun registration(auth: AuthModel): Resource<ResponseRegisterModel> =
        getResult {
            withContext(Dispatchers.IO) {
                authService.registration(auth)
            }
        }
}