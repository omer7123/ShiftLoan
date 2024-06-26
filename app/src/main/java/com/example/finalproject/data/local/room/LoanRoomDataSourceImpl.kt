package com.example.finalproject.data.local.room

import com.example.finalproject.data.model.LoanModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoanRoomDataSourceImpl @Inject constructor(private val db: AppDatabase) : LoanRoomDataSource {
    override suspend fun getAll(): List<LoanModel> {
        val list: List<LoanModel>
        withContext(Dispatchers.IO) {
            list = db.loanDao().getAll()
        }
        return list
    }

    override suspend fun saveAll(loans: List<LoanModel>) {
        withContext(Dispatchers.IO) {
            db.loanDao().replaceAll(loans)
        }
    }

    override suspend fun deleteAllLoans() {
        withContext(Dispatchers.IO) {
            db.loanDao().clearContacts()
        }
    }

    override suspend fun getLoanById(id: Int): LoanModel {
        val result: LoanModel
        withContext(Dispatchers.IO) {
            result = db.loanDao().getLoanById(id)
        }
        return result
    }
}