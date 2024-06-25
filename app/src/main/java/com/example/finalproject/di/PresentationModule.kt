package com.example.finalproject.di

import androidx.lifecycle.ViewModel
import com.example.finalproject.presentation.mainActivity.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {
    @Binds
    @[IntoMap ClassKey(MainViewModel::class)]
    fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel
}