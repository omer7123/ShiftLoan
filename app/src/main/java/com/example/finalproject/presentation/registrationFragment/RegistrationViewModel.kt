package com.example.finalproject.presentation.registrationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.domain.useCase.LoginUseCase
import com.example.finalproject.domain.useCase.RegistrationUseCase
import com.example.finalproject.domain.useCase.SaveTokenUseCase
import com.example.finalproject.ui.registrationFragment.RegistrationFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val loginUseCase: LoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    private lateinit var errorCallback: ValidationErrorCallback

    private val _screenState: MutableLiveData<RegistrationScreenState> =
        MutableLiveData(RegistrationScreenState.Initial)
    val screenState: LiveData<RegistrationScreenState> = _screenState

    private val _registrationStatus: MutableLiveData<RegistrationStatusState> =
        MutableLiveData()
    val registrationStatus: LiveData<RegistrationStatusState> = _registrationStatus

    fun setValidationErrorCallback(callback: RegistrationFragment) {
        errorCallback = callback
    }

    fun register(name: String, password: String, confirmPassword: String) {
        if (validationData(name, password, confirmPassword)) {
            viewModelScope.launch {
                _screenState.value = RegistrationScreenState.Loading
                val auth = AuthEntity(name, password)
                when (val result = registrationUseCase(auth)) {
                    is Resource.Error -> _registrationStatus.value =
                        RegistrationStatusState.Error(result.msg.toString(), result.responseCode)

                    is Resource.Success -> authorization(auth)
                }
            }
        }
    }

    private suspend fun authorization(auth: AuthEntity) {
        when (val result = loginUseCase(auth)) {
            is Resource.Error -> _registrationStatus.value =
                RegistrationStatusState.Error(result.msg.toString(), null)

            is Resource.Success -> {
                saveTokenUseCase(result.data)
                _registrationStatus.value = RegistrationStatusState.Success
            }
        }
    }

    private fun validationData(name: String, password: String, confirmPassword: String): Boolean {
        val nameError = if (name.isEmpty() || !name.matches(Regex("^[a-zA-Z0-9]*$"))) {
            if (name.isEmpty()) errorCallback.getNameEmptyError() else errorCallback.getInvalidNameError()
        } else null

        val passwordError = if (password.isEmpty()) {
            errorCallback.getPasswordEmptyError()
        } else null

        val confirmPasswordError = if (confirmPassword.isEmpty() || password != confirmPassword) {
            if (confirmPassword.isEmpty()) errorCallback.getConfirmPasswordEmptyError() else errorCallback.getPasswordsDoNotMatchError()
        } else null

        if (nameError != null || passwordError != null || confirmPasswordError != null) {
            _screenState.value = RegistrationScreenState.ValidationErrorContent(
                nameError = nameError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
            return false
        }
        return true
    }
}