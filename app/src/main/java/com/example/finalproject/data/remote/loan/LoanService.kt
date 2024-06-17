package com.example.finalproject.data.remote.loan

import com.example.finalproject.data.model.LoanConditionsModel
import retrofit2.Response

import retrofit2.http.GET

interface LoanService {

    @GET("/loans/conditions")
    suspend fun getLoanConditions(): Response<LoanConditionsModel>
}