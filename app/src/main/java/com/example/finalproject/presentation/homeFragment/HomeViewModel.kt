package com.example.finalproject.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    private val _screenState: MutableLiveData<HomeScreenState> =
        MutableLiveData(HomeScreenState.Initial)
    val screenState: LiveData<HomeScreenState> = _screenState

//    fun checkValue(value: String) {
//        val number = value.toIntOrNull()
//        val message = if (number!! > 10000 || number < 1000) {
//            if (number>10000) "Максимум 10 000 ₽" else "Минимум 1 000 ₽"
//        } else {
//            ""
//        }
//        val currentState = _screenState.value
//        if (currentState is HomeScreenState.Content) {
//            _screenState.value = currentState.copy(validationMsg = message)
//        } else {
//            _screenState.value = HomeScreenState.Content(validationMsg = message, value)
//        }
//    }


}