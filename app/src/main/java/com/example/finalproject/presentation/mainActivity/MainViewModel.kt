package com.example.finalproject.presentation.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.useCase.GetLocaleUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val getLocaleUseCase: GetLocaleUseCase) :
    ViewModel() {

    private val _locale = MutableLiveData<String>()
    val locale: LiveData<String> = _locale

    fun getLocale() {
        viewModelScope.launch {
            val locale = getLocaleUseCase()
            _locale.value = locale
        }
    }
}