package com.example.finalproject.presentation.homeAuthenticationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.useCase.GetSharedPrefAuthUseCase
import com.example.finalproject.domain.useCase.LoginUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeAuthenticationViewModel @Inject constructor(
    private val getSharedPrefAuthUseCase: GetSharedPrefAuthUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _authStatus: MutableLiveData<HomeAuthenticationScreenState> = MutableLiveData()
    val authStatus: LiveData<HomeAuthenticationScreenState> = _authStatus

    private val handler = CoroutineExceptionHandler { _, error ->
        _authStatus.value = HomeAuthenticationScreenState.Error(error.toString())
    }

    fun auth() {
        viewModelScope.launch(handler) {
            _authStatus.value = HomeAuthenticationScreenState.Loading

            val auth = getSharedPrefAuthUseCase()
            if (auth.name.isNotEmpty() && auth.password.isNotEmpty()) {
                when (val result = loginUseCase(auth)) {
                    is Resource.Error -> _authStatus.value =
                        HomeAuthenticationScreenState.Error(result.msg.toString())

                    Resource.Loading -> _authStatus.value = HomeAuthenticationScreenState.Loading
                    is Resource.Success -> _authStatus.value = HomeAuthenticationScreenState.Success
                }
            }else{
                _authStatus.value = HomeAuthenticationScreenState.Error("Авторизуйтесь, пожалуйста")
            }
        }
    }
}