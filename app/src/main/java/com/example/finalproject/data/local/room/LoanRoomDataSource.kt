package com.example.finalproject.data.local.room

import com.example.finalproject.data.model.LoanModel

interface LoanRoomDataSource {

    suspend fun getAll(): List<LoanModel>
    suspend fun saveAll(loans: List<LoanModel>)
    suspend fun deleteAllLoans()
    suspend fun getLoanById(id: Int): LoanModel
}
