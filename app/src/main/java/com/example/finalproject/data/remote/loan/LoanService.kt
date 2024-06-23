package com.example.finalproject.data.remote.loan

import com.example.finalproject.data.model.LoanConditionsModel
import com.example.finalproject.data.model.LoanModel
import com.example.finalproject.data.model.LoanRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoanService {

    @GET("/loans/conditions")
    suspend fun getLoanConditions(): Response<LoanConditionsModel>

    @GET("/loans/all")
    suspend fun getLoansAll(): Response<List<LoanModel>>

    @GET("/loans/{id}")
    suspend fun getLoan(@Path("id") loanId: Int): Response<LoanModel>

    @POST("/loans")
    suspend fun createLoan(@Body loan: LoanRequestModel): Response<LoanModel>
}