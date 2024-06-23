package com.example.finalproject.presentation.newLoanFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.core.Resource
import com.example.finalproject.domain.entity.LoanRequestEntity
import com.example.finalproject.domain.useCase.CreateLoanUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewLoanViewModel @Inject constructor(private val createLoanUseCase: CreateLoanUseCase) :
    ViewModel() {

    private val _createNewLoanStatus: MutableLiveData<CreateNewLoanStatusState> = MutableLiveData()
    val createNewLoanStatus: LiveData<CreateNewLoanStatusState> = _createNewLoanStatus

    private val handler = CoroutineExceptionHandler { _, _ ->
        _createNewLoanStatus.value =
            CreateNewLoanStatusState.Error("Произошла ошибка, попробуйте еще раз")
    }

    fun createLoan(loan: LoanRequestEntity) {
        _createNewLoanStatus.value = CreateNewLoanStatusState.Loading
        viewModelScope.launch(handler) {
            when (val result = createLoanUseCase(loan)) {
                is Resource.Error -> _createNewLoanStatus.value =
                    CreateNewLoanStatusState.Error(result.msg.toString())

                Resource.Loading -> _createNewLoanStatus.value = CreateNewLoanStatusState.Loading
                is Resource.Success -> _createNewLoanStatus.value =
                    CreateNewLoanStatusState.Success(result.data)
            }
        }
    }
}