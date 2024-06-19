package com.example.finalproject.data.converters

import com.example.finalproject.data.model.LoanModel
import com.example.finalproject.domain.entity.LoanEntity

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