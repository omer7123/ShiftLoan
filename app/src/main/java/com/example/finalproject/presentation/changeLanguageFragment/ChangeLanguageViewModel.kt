package com.example.finalproject.presentation.changeLanguageFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.useCase.GetLocaleUseCase
import com.example.finalproject.domain.useCase.SaveLocaleUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangeLanguageViewModel @Inject constructor(
    private val saveLocaleUseCase: SaveLocaleUseCase,
    private val getLocaleUseCase: GetLocaleUseCase
) :
    ViewModel() {

    private val _screenState: MutableLiveData<ChangeLanguageScreenState> =
        MutableLiveData(ChangeLanguageScreenState.Initial)
    val screenState: LiveData<ChangeLanguageScreenState> = _screenState

    private val handler = CoroutineExceptionHandler { _, _ ->
        _screenState.value = ChangeLanguageScreenState.Error("Произошла непредвиденная ошибка")
    }

    fun getLocale() {
        viewModelScope.launch(handler) {
            _screenState.value = ChangeLanguageScreenState.Content(getLocaleUseCase())
        }
    }

    fun saveLocale(locale: String) {
        viewModelScope.launch(handler) {
            saveLocaleUseCase(locale)
            _screenState.value = ChangeLanguageScreenState.Content(locale)
        }
    }
}