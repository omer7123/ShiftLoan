package com.example.finalproject.di

import androidx.lifecycle.ViewModel
import com.example.finalproject.presentation.changeLanguageFragment.ChangeLanguageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface SettingsModule {
    @Binds
    @[IntoMap ClassKey(ChangeLanguageViewModel::class)]
    fun provideChangeLanguageViewModel(changeLanguageViewModel: ChangeLanguageViewModel): ViewModel
}