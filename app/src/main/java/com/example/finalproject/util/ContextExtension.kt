package com.example.finalproject.util

import android.content.Context
import android.widget.Toast
import com.example.finalproject.App
import com.example.finalproject.di.AppComponent

fun Context.showToast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.getAppComponent(): AppComponent = (this.applicationContext as App).appComponent
