package com.example.finalproject.di

import android.content.Context
import com.example.finalproject.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class, PresentationModule::class, DataSourceModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun apiUrlProvider(): ApiUrlProvider
    fun authenticationComponent(): AuthenticationComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope