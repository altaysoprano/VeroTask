package com.example.verotask.network

import com.example.verotask.data.models.LoginRequest
import com.example.verotask.data.models.LoginResponse
import com.example.verotask.data.models.Task
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("tasks/select")
    suspend fun getTasks(): Response<List<Task>>
}
