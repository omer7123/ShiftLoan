package com.example.finalproject.data.repository

import com.example.finalproject.core.Resource
import com.example.finalproject.data.local.encryptedSharedPref.AuthenticationSharedPrefDataSource
import com.example.finalproject.data.model.AuthModel
import com.example.finalproject.data.remote.authentication.AuthenticationDataSource
import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.domain.entity.ResponseRegisterEntity
import com.example.finalproject.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource,
    private val authenticationSharedPrefDataSource: AuthenticationSharedPrefDataSource
) : AuthenticationRepository {

    override suspend fun login(auth: AuthEntity): Resource<String> {
        return authenticationDataSource.login(AuthModel(auth.name, auth.password))
    }

    override suspend fun registration(auth: AuthEntity): Resource<ResponseRegisterEntity> {
        return when (val result =
            authenticationDataSource.registration(AuthModel(auth.name, auth.password))) {
            is Resource.Error -> Resource.Error(result.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(ResponseRegisterEntity(result.data.name))
        }
    }

    override suspend fun getAuth(): AuthEntity {
        val authModel = authenticationSharedPrefDataSource.getAuth()
        return AuthEntity(authModel.name, authModel.password)
    }

    override suspend fun saveAuth(auth: AuthEntity) {
        authenticationSharedPrefDataSource.saveAuth(auth.name, auth.password)
    }

    override suspend fun saveToken(token: String) {
        authenticationSharedPrefDataSource.saveToken(token)
    }

    override suspend fun getToken(): String {
        return authenticationSharedPrefDataSource.getToken()
    }

    override suspend fun deleteToken() {
        authenticationSharedPrefDataSource.deleteToken()
    }
}