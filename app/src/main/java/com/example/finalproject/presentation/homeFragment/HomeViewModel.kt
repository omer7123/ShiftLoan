package com.example.finalproject.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.LoanConditionsEntity
import com.example.finalproject.domain.useCase.DeleteAllLoansInRoomUseCase
import com.example.finalproject.domain.useCase.GetLoanConditionsUseCase
import com.example.finalproject.domain.useCase.GetLoansAllUseCase
import com.example.finalproject.domain.useCase.GetLoansFromRoomUseCase
import com.example.finalproject.domain.useCase.SaveLoansToRoomUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase,
    private val getLoansAllUseCase: GetLoansAllUseCase,
    private val getLoansFromRoomUseCase: GetLoansFromRoomUseCase,
    private val saveLoansToRoomUseCase: SaveLoansToRoomUseCase,
    private val deleteAllLoansInRoomUseCase: DeleteAllLoansInRoomUseCase
) :
    ViewModel() {

    private val _screenState: MutableLiveData<HomeScreenState> =
        MutableLiveData(HomeScreenState.Initial)
    val screenState: LiveData<HomeScreenState> = _screenState

    fun getLoanConditions() {
        _screenState.value = HomeScreenState.Loading
        viewModelScope.launch {
            when (val result = getLoanConditionsUseCase()) {
                is Resource.Error -> {
                    _screenState.value =
                        HomeScreenState.Error(result.msg.toString())
                    getLoansFromRoom()
                }

                is Resource.Success -> {
                    getLoansAll(result.data)
                }
            }
        }
    }

    private fun getLoansFromRoom() {
        viewModelScope.launch {
            val result = getLoansFromRoomUseCase()
            val smallList = result.take(3)
            _screenState.value = HomeScreenState.Content("7000", smallList, null)
        }
    }

    private suspend fun getLoansAll(conditions: LoanConditionsEntity) {
        when (val result = getLoansAllUseCase()) {
            is Resource.Error -> _screenState.value = HomeScreenState.Error(result.msg.toString())

            is Resource.Success -> {
                val smallCountLoans = result.data.take(3)
                deleteAllLoansInRoomUseCase()
                saveLoansToRoomUseCase(result.data.reversed())
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