package com.example.finalproject.presentation.registrationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.domain.useCase.LoginUseCase
import com.example.finalproject.domain.useCase.RegistrationUseCase
import com.example.finalproject.domain.useCase.SaveSharedPrefAuthUseCase
import com.example.finalproject.domain.useCase.SaveTokenUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val loginUseCase: LoginUseCase,
    private val saveSharedPrefAuthUseCase: SaveSharedPrefAuthUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    private val _screenState: MutableLiveData<RegistrationScreenState> =
        MutableLiveData(RegistrationScreenState.Initial)
    val screenState: LiveData<RegistrationScreenState> = _screenState


    fun register(name: String, password: String, confirmPassword: String) {

        if (validationData(name, password, confirmPassword)) {
            viewModelScope.launch {
                _screenState.value = RegistrationScreenState.Loading
                val auth = AuthEntity(name, password)
                when (val result = registrationUseCase(auth)) {
                    is Resource.Error -> _screenState.value =
                        RegistrationScreenState.Error(result.msg.toString())

                    Resource.Loading -> _screenState.value = RegistrationScreenState.Loading
                    is Resource.Success -> authorization(auth)
                }
            }
        }
    }

    private suspend fun authorization(auth: AuthEntity) {
        when (val result = loginUseCase(auth)) {
            is Resource.Error -> _screenState.value =
                RegistrationScreenState.Error(result.msg.toString())

            Resource.Loading -> _screenState.value = RegistrationScreenState.Loading
            is Resource.Success -> {
                saveSharedPrefAuthUseCase(auth)
                saveTokenUseCase(result.data)
                _screenState.value = RegistrationScreenState.Success
            }
        }
    }

    private fun validationData(name: String, password: String, confirmPassword: String): Boolean {
        val nameError = if (name.isEmpty() || !name.matches(Regex("^[a-zA-Z0-9]*$"))) {
            if (name.isEmpty()) "Поле не может быть пустым" else "Только цифры и латинские буквы"
        } else null

        val passwordError = if (password.isEmpty()) {
            "Поле не может быть пустым"
        } else null

        val confirmPasswordError = if (confirmPassword.isEmpty() || password != confirmPassword) {
            if (confirmPassword.isEmpty()) "Поле не может быть пустым" else "Пароли не совпадают"
        } else null

        if (nameError != null || passwordError != null || confirmPasswordError != null) {
            _screenState.value = RegistrationScreenState.ValidationError(
                nameError = nameError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
            return false
        }
        return true
    }
}