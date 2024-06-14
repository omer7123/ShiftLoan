package com.example.finalproject.di

import com.example.finalproject.data.repository.AuthenticationRepositoryImpl
import com.example.finalproject.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Binds
    @Singleton
    fun bindAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository
}