package com.acms.whatnow.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://gnews.io/api/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
