package com.example.finalproject

import android.app.Application
import com.example.finalproject.di.AppComponent
import com.example.finalproject.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}