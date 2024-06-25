package com.example.finalproject.presentation.loanDetailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.useCase.GetLoanByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanDetailViewModel @Inject constructor(private val getLoanByIdUseCase: GetLoanByIdUseCase) :
    ViewModel() {

    private val _stateScreen: MutableLiveData<LoadDetailScreenState> =
        MutableLiveData(LoadDetailScreenState.Initial)
    val screenState: LiveData<LoadDetailScreenState> = _stateScreen

    fun getLoanById(id: Int) {
        _stateScreen.value = LoadDetailScreenState.Loading
        viewModelScope.launch {
            when (val result = getLoanByIdUseCase(id)) {
                is Resource.Error -> _stateScreen.value =
                    LoadDetailScreenState.Error(result.msg.toString())

                is Resource.Success -> _stateScreen.value =
                    LoadDetailScreenState.Content(result.data)
            }
        }
    }
}