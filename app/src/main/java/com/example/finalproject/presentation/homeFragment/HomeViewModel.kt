package com.example.finalproject.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.useCase.GetLoanConditionsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getLoanConditionsUseCase: GetLoanConditionsUseCase) :
    ViewModel() {

    private val _screenState: MutableLiveData<HomeScreenState> =
        MutableLiveData(HomeScreenState.Initial)
    val screenState: LiveData<HomeScreenState> = _screenState

    fun getLoanConditions() {
        viewModelScope.launch {
            when (val result = getLoanConditionsUseCase()) {
                is Resource.Error -> _screenState.value =
                    HomeScreenState.Error(result.msg.toString())

                Resource.Loading -> {}
                is Resource.Success -> _screenState.value =
                    HomeScreenState.Content("7000", null, result.data)
            }
        }
    }

    fun setValueLoan(value: String) {

        val currentState = _screenState.value
        if (currentState is HomeScreenState.Content) {
            _screenState.value = currentState.copy(sumLoan = value)
        }
    }
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