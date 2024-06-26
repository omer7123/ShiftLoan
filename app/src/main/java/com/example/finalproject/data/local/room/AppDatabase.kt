package com.example.finalproject.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalproject.data.model.LoanModel

@Database(entities = [LoanModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loanDao(): LoanDao
}