package com.example.verotask.network

import com.example.verotask.data.models.LoginRequest
import com.example.verotask.data.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body credentials: LoginRequest): Call<LoginResponse>
}
