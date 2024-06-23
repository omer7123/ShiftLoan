package com.example.finalproject.data.converters

import com.example.finalproject.data.model.LoanModel
import com.example.finalproject.data.model.LoanRequestModel
import com.example.finalproject.domain.entity.LoanEntity
import com.example.finalproject.domain.entity.LoanRequestEntity

fun LoanModel.toLoanEntity(): LoanEntity {
    return LoanEntity(
        amount = this.amount,
        date = this.date,
        firstName = this.firstName,
        id = this.id,
        lastName = this.lastName,
        percent = this.percent,
        period = this.period,
        phoneNumber = this.phoneNumber,
        state = this.state
    )
}

fun LoanRequestEntity.toLoanRequestModel(): LoanRequestModel {
    return LoanRequestModel(
        amount = this.amount.toFloat(),
        firstName = this.firstName,
        lastName = this.lastName,
        percent = this.percent,
        period = this.period,
        phoneNumber = this.phoneNumber,
    )
}