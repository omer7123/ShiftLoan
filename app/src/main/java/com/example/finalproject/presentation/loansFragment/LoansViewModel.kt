package com.example.finalproject.presentation.loansFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.useCase.DeleteAllLoansInRoomUseCase
import com.example.finalproject.domain.useCase.GetLoansAllUseCase
import com.example.finalproject.domain.useCase.GetLoansFromRoomUseCase
import com.example.finalproject.domain.useCase.SaveLoansToRoomUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoansViewModel @Inject constructor(
    private val getLoansAllUseCase: GetLoansAllUseCase,
    private val getLoansFromRoomUseCase: GetLoansFromRoomUseCase,
    private val saveLoansToRoomUseCase: SaveLoansToRoomUseCase,
    private val deleteAllLoansInRoomUseCase: DeleteAllLoansInRoomUseCase
) :
    ViewModel() {

    private val _screenState: MutableLiveData<LoansScreenState> =
        MutableLiveData(LoansScreenState.Initial)
    val screenState: LiveData<LoansScreenState> = _screenState

    fun getLoans() {
        viewModelScope.launch {
            _screenState.value = LoansScreenState.Loading
            when (val result = getLoansAllUseCase()) {
                is Resource.Error -> {
                    _screenState.value = LoansScreenState.Error(result.msg.toString())

                    val list = getLoansFromRoomUseCase()
                    _screenState.value =
                        LoansScreenState.Content(list.reversed())
                }

                is Resource.Success -> {
                    deleteAllLoansInRoomUseCase()
                    saveLoansToRoomUseCase(result.data.reversed())
                    _screenState.value = LoansScreenState.Content(result.data)
                }
            }
        }
    }
}