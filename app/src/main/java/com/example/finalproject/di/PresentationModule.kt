package com.example.finalproject.di

import androidx.lifecycle.ViewModel
import com.example.finalproject.presentation.authorizationFragment.AuthorizationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {

    @Binds
    @[IntoMap ClassKey(AuthorizationViewModel::class)]
    fun provideAuthorizationViewModel(authorizationViewModel: AuthorizationViewModel): ViewModel
}