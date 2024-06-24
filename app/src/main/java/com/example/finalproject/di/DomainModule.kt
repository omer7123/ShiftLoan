package com.example.finalproject.di

import com.example.finalproject.data.repository.AuthenticationRepositoryImpl
import com.example.finalproject.data.repository.LoanRepositoryImpl
import com.example.finalproject.data.repository.SettingsRepositoryImpl
import com.example.finalproject.domain.repository.AuthenticationRepository
import com.example.finalproject.domain.repository.LoanRepository
import com.example.finalproject.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Binds
    @Singleton
    fun bindAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    @Singleton
    fun bindLoanRepository(impl: LoanRepositoryImpl): LoanRepository

    @Binds
    @Singleton
    fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}