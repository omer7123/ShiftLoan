package com.example.finalproject.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkReceiver : BroadcastReceiver() {
    private val _isConnected: MutableLiveData<Boolean> = MutableLiveData()
    val isConnected: LiveData<Boolean> = _isConnected

    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        _isConnected.value = activeNetwork?.isConnected == true
    }
}