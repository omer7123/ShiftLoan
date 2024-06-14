package com.example.finalproject.di

import com.example.finalproject.data.remote.AuthenticationDataSource
import com.example.finalproject.data.remote.AuthenticationDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindAuthenticationDataSource(impl: AuthenticationDataSourceImpl): AuthenticationDataSource
}