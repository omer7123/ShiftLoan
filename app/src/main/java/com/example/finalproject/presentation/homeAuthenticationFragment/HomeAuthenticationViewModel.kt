package com.example.finalproject.presentation.homeAuthenticationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.useCase.GetLoansAllUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeAuthenticationViewModel @Inject constructor(
    private val getLoansAllUseCase: GetLoansAllUseCase
) : ViewModel() {

    private val _authStatus: MutableLiveData<HomeAuthenticationAuthStatus> = MutableLiveData()
    val authStatus: LiveData<HomeAuthenticationAuthStatus> = _authStatus

    private val handler = CoroutineExceptionHandler { _, _ ->
        _authStatus.value = HomeAuthenticationAuthStatus.Error
    }

    fun auth() {
        viewModelScope.launch(handler) {
            _authStatus.value = HomeAuthenticationAuthStatus.Loading

            when (getLoansAllUseCase()) {
                is Resource.Error -> _authStatus.value = HomeAuthenticationAuthStatus.Error
                Resource.Loading -> _authStatus.value = HomeAuthenticationAuthStatus.Loading
                is Resource.Success -> _authStatus.value = HomeAuthenticationAuthStatus.Success
            }
        }
    }
}