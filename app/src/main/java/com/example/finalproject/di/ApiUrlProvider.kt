package com.example.finalproject.di

import javax.inject.Inject

interface ApiUrlProvider {
    var url: String
}

class ApiUrlProviderImpl @Inject constructor() : ApiUrlProvider {

    override var url =
        "https://shift-loan-app.exodar.heartlessguy.pro"
}