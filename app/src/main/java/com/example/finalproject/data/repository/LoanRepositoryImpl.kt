package com.example.finalproject.data.repository

import com.example.finalproject.core.Resource
import com.example.finalproject.data.converters.toLoanEntity
import com.example.finalproject.data.converters.toLoanRequestModel
import com.example.finalproject.data.remote.loan.LoanDataSource
import com.example.finalproject.domain.entity.LoanConditionsEntity
import com.example.finalproject.domain.entity.LoanEntity
import com.example.finalproject.domain.entity.LoanRequestEntity
import com.example.finalproject.domain.repository.LoanRepository
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(private val dataSource: LoanDataSource) :
    LoanRepository {
    override suspend fun getLoanConditions(): Resource<LoanConditionsEntity> {
        return when (val result =
            dataSource.getLoanConditions()) {
            is Resource.Error -> Resource.Error(result.msg.toString(), null, result.responseCode)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(
                LoanConditionsEntity(
                    result.data.percent,
                    result.data.period,
                    result.data.maxAmount
                )
            )
        }
    }

    override suspend fun getLoansAll(): Resource<List<LoanEntity>> {
        return when (val result = dataSource.getLoansAll()) {
            is Resource.Error -> Resource.Error(result.msg.toString(), null, result.responseCode)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> {
                val listLoanEntity = result.data.map { it.toLoanEntity() }
                return Resource.Success(listLoanEntity)
            }
        }
    }

    override suspend fun getLoan(id: Int): Resource<LoanEntity> {
        return when (val result = dataSource.getLoan(id)) {
            is Resource.Error -> Resource.Error(result.msg.toString(), null, result.responseCode)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.toLoanEntity())
        }
    }

    override suspend fun createLoan(loan: LoanRequestEntity): Resource<LoanEntity> {
        return when (val result = dataSource.createLoan(loan.toLoanRequestModel())) {
            is Resource.Error -> Resource.Error(result.msg.toString(), null, result.responseCode)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.toLoanEntity())
        }
    }
}