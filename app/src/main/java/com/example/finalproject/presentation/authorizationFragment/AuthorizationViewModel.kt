package com.example.finalproject.presentation.authorizationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.domain.useCase.LoginUseCase
import com.example.finalproject.domain.useCase.SaveTokenUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    private val _authorizationStatusState: MutableLiveData<AuthorizationStatusState> =
        MutableLiveData()
    val screenState: LiveData<AuthorizationStatusState> = _authorizationStatusState

    private val handler = CoroutineExceptionHandler { _, _ ->
        _authorizationStatusState.value = AuthorizationStatusState.Error(null, null)
    }

    fun auth(auth: AuthEntity) {
        viewModelScope.launch(handler) {
            _authorizationStatusState.value = AuthorizationStatusState.Loading
            when (val result = loginUseCase(auth)) {
                is Resource.Error -> _authorizationStatusState.value =
                    AuthorizationStatusState.Error(result.msg.toString(), result.responseCode)

                is Resource.Success -> renderSuccess(result.data)
            }
        }
    }

    private suspend fun renderSuccess(token: String) {
        saveTokenUseCase(token)
        _authorizationStatusState.value = AuthorizationStatusState.Success
    }

}