package com.example.finalproject.presentation.loansFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.useCase.GetLoansAllUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoansViewModel @Inject constructor(private val getLoansAllUseCase: GetLoansAllUseCase) :
    ViewModel() {

    private val _screenState: MutableLiveData<LoansScreenState> =
        MutableLiveData(LoansScreenState.Initial)
    val screenState: LiveData<LoansScreenState> = _screenState

    fun getLoans() {
        viewModelScope.launch {
            _screenState.value = LoansScreenState.Loading
            when (val result = getLoansAllUseCase()) {
                is Resource.Error -> _screenState.value =
                    LoansScreenState.Error(result.msg.toString())

                Resource.Loading -> _screenState.value = LoansScreenState.Loading
                is Resource.Success -> _screenState.value = LoansScreenState.Content(result.data)
            }
        }
    }
}