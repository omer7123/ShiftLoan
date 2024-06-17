package com.example.finalproject.di

import com.example.finalproject.data.local.encryptedSharedPref.AuthenticationSharedPrefDataSource
import com.example.finalproject.data.local.encryptedSharedPref.AuthenticationSharedPrefDataSourceImpl
import com.example.finalproject.data.remote.authentication.AuthenticationDataSource
import com.example.finalproject.data.remote.authentication.AuthenticationDataSourceImpl
import com.example.finalproject.data.remote.loan.LoanDataSource
import com.example.finalproject.data.remote.loan.LoanDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindAuthenticationDataSource(impl: AuthenticationDataSourceImpl): AuthenticationDataSource

    @Binds
    @Singleton
    fun bindAuthenticationSharedPrefDataSource(impl: AuthenticationSharedPrefDataSourceImpl): AuthenticationSharedPrefDataSource

    @Binds
    @Singleton
    fun bindLoanDataSource(impl: LoanDataSourceImpl): LoanDataSource
}