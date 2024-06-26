package com.example.finalproject.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalproject.data.model.LoanModel

@Dao
interface LoanDao {

    @Query("SELECT * FROM loanmodel")
    suspend fun getAll(): List<LoanModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replaceAll(loans: List<LoanModel>)

    @Query("DELETE FROM loanmodel")
    suspend fun clearContacts()

    @Query("SELECT * FROM loanmodel WHERE id = :id LIMIT 1")
    suspend fun getLoanById(id: Int): LoanModel
}