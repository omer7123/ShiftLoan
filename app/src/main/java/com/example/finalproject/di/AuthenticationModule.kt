package com.example.finalproject.di

import androidx.lifecycle.ViewModel
import com.example.finalproject.presentation.authorizationFragment.AuthorizationViewModel
import com.example.finalproject.presentation.homeAuthenticationFragment.HomeAuthenticationViewModel
import com.example.finalproject.presentation.menuFragment.MenuViewModel
import com.example.finalproject.presentation.registrationFragment.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface AuthenticationModule {
    @Binds
    @[IntoMap ClassKey(AuthorizationViewModel::class)]
    fun provideAuthorizationViewModel(authorizationViewModel: AuthorizationViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(RegistrationViewModel::class)]
    fun provideRegistrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(HomeAuthenticationViewModel::class)]
    fun provideHomeAuthenticationViewModel(homeAuthenticationViewModel: HomeAuthenticationViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(MenuViewModel::class)]
    fun provideMenuViewModel(menuViewModel: MenuViewModel): ViewModel
}