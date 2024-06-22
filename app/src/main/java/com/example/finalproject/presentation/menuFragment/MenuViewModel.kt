package com.example.finalproject.presentation.menuFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.useCase.DeleteTokenUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuViewModel @Inject constructor(private val deleteTokenUseCase: DeleteTokenUseCase) :
    ViewModel() {

    private val _logOutStatus: MutableLiveData<LogOutStatusState> = MutableLiveData()
    val logOutStatus: LiveData<LogOutStatusState> = _logOutStatus

    private val handler = CoroutineExceptionHandler { _, throwable ->
        _logOutStatus.value = LogOutStatusState.Error(throwable.message.toString())
    }

    fun logOut() {
        viewModelScope.launch(handler) {
            deleteTokenUseCase()
            _logOutStatus.value = LogOutStatusState.Success
        }
    }
}