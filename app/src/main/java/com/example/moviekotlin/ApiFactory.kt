package com.example.moviekotlin

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {

    companion object {
        private val BASE_URL = "https://api.kinopoisk.dev/v1.4/"
        private var apiService: ApiService? = null

        fun getInstance(): ApiService? {
            if (apiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService
        }
    }
}