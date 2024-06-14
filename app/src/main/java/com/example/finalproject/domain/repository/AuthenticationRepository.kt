package com.example.finalproject.domain.repository

import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.domain.entity.ResponseRegisterEntity

interface AuthenticationRepository {
    suspend fun login(auth: AuthEntity): Resource<String>
    suspend fun registration(auth: AuthEntity): Resource<ResponseRegisterEntity>
}