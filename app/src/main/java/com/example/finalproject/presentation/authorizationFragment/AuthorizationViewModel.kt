package com.example.finalproject.presentation.authorizationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.domain.useCase.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _authorizationState: MutableLiveData<AuthorizationStatusState> = MutableLiveData(AuthorizationStatusState.Initial)
    val authorizationState: LiveData<AuthorizationStatusState> = _authorizationState

    fun auth(auth: AuthEntity){
        viewModelScope.launch {
            _authorizationState.value = AuthorizationStatusState.Loading
            when(val result = loginUseCase(auth)){
                is Resource.Error -> _authorizationState.value = AuthorizationStatusState.Error(result.msg.toString())
                Resource.Loading -> _authorizationState.value = AuthorizationStatusState.Loading
                is Resource.Success -> _authorizationState.value = AuthorizationStatusState.Success
            }
        }
    }
}