package com.example.finalproject.data.remote.loan

import android.content.Context
import com.example.finalproject.core.BaseDataSource
import com.example.finalproject.core.Resource
import com.example.finalproject.data.model.LoanConditionsModel
import com.example.finalproject.data.model.LoanModel
import com.example.finalproject.data.model.LoanRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoanDataSourceImpl @Inject constructor(
    private val loanService: LoanService,
    context: Context
) : LoanDataSource,
    BaseDataSource(context) {
    override suspend fun getLoanConditions(): Resource<LoanConditionsModel> = getResult {
        withContext(Dispatchers.IO) {
            loanService.getLoanConditions()
        }
    }

    override suspend fun getLoansAll(): Resource<List<LoanModel>> = getResult {
        withContext(Dispatchers.IO) {
            loanService.getLoansAll()
        }
    }

    override suspend fun getLoan(id: Int): Resource<LoanModel> = getResult {
        withContext(Dispatchers.IO) {
            loanService.getLoan(id)
        }
    }

    override suspend fun createLoan(loan: LoanRequestModel): Resource<LoanModel> = getResult {
        withContext(Dispatchers.IO) {
            loanService.createLoan(loan)
        }
    }
}