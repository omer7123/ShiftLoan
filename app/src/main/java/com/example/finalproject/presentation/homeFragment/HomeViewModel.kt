package com.example.finalproject.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.LoanConditionsEntity
import com.example.finalproject.domain.useCase.GetLoanConditionsUseCase
import com.example.finalproject.domain.useCase.GetLoansAllUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase,
    private val getLoansAllUseCase: GetLoansAllUseCase
) :
    ViewModel() {

    private val _screenState: MutableLiveData<HomeScreenState> =
        MutableLiveData(HomeScreenState.Initial)
    val screenState: LiveData<HomeScreenState> = _screenState

    fun getLoanConditions() {
        _screenState.value = HomeScreenState.Loading
        viewModelScope.launch {
            when (val result = getLoanConditionsUseCase()) {
                is Resource.Error -> _screenState.value =
                    HomeScreenState.Error(result.msg.toString())

                Resource.Loading -> {}
                is Resource.Success -> {
//                    _screenState.value =
//                        HomeScreenState.Content("7000", null, result.data)
                    getLoansAll(result.data)
                }
            }
        }
    }

    private suspend fun getLoansAll(conditions: LoanConditionsEntity) {

        when (val result = getLoansAllUseCase()) {
            is Resource.Error -> _screenState.value = HomeScreenState.Error(result.msg.toString())
            Resource.Loading -> _screenState.value = HomeScreenState.Loading
            is Resource.Success -> {
                val smallCountLoans = result.data.take(3)
                _screenState.value =
                    HomeScreenState.Content("7000", smallCountLoans, conditions)
            }
        }
    }

    fun setValueLoan(value: String) {
        val currentState = _screenState.value
        if (currentState is HomeScreenState.Content) {
            _screenState.value = currentState.copy(sumLoan = value)
        }
    }
}