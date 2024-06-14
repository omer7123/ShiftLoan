package com.example.finalproject.presentation.multiViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class MultiViewModelFactory @Inject constructor(
    private val viewModelFactories: Map<Class<*>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelFactories.getValue(modelClass as Class<*>).get() as T
    }

}