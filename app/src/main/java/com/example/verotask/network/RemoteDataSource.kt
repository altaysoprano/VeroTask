package com.example.verotask.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    companion object {
        private const val BASE_URL = "https://api.baubuddy.de/dev/index.php/v1/" // API'nin gerçek base URL'i

        fun create(): AuthApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(AuthApi::class.java)
        }
    }
}
