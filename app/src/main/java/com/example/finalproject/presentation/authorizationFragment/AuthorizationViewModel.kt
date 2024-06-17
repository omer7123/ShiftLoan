package com.example.finalproject.presentation.authorizationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.domain.useCase.LoginUseCase
import com.example.finalproject.domain.useCase.SaveSharedPrefAuthUseCase
import com.example.finalproject.domain.useCase.SaveTokenUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveSharedPrefAuthUseCase: SaveSharedPrefAuthUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    private val _screenState: MutableLiveData<AuthorizationScreenState> =
        MutableLiveData(AuthorizationScreenState.Initial)
    val screenState: LiveData<AuthorizationScreenState> = _screenState

    val handler = CoroutineExceptionHandler { _, error ->
        _screenState.value = AuthorizationScreenState.Error("Произошла ошибка, попробуйте еще раз")
    }

    fun auth(auth: AuthEntity) {
        if (validationData(auth.name, auth.password)) {
            viewModelScope.launch(handler) {
                _screenState.value = AuthorizationScreenState.Loading
                when (val result = loginUseCase(auth)) {
                    is Resource.Error -> _screenState.value =
                        AuthorizationScreenState.Error(result.msg.toString())

                    Resource.Loading -> _screenState.value = AuthorizationScreenState.Loading
                    is Resource.Success -> renderSuccess(auth, result.data)
                }
            }
        }
    }

    private suspend fun renderSuccess(auth: AuthEntity, token: String) {
        saveSharedPrefAuthUseCase(auth)
        saveTokenUseCase(token)
        _screenState.value = AuthorizationScreenState.Success
    }

    private fun validationData(name: String, password: String): Boolean {
        val nameError = if (name.isEmpty()) {
            "Поле не может быть пустым"
        } else null

        val passwordError = if (password.isEmpty()) {
            "Поле не может быть пустым"
        } else null

        if (nameError != null || passwordError != null) {
            _screenState.value = AuthorizationScreenState.ValidationError(
                nameError = nameError,
                passwordError = passwordError,
            )
            return false
        }
        return true
    }
}