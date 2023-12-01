package com.example.verotask.data.repository

import com.example.verotask.network.ApiService
import com.example.verotask.data.models.LoginRequest
import com.example.verotask.data.models.LoginResponse

class LoginRepositoryImp(private val apiService: ApiService): LoginRepository {

    override suspend fun login(username: String, password: String): LoginResponse {
        val request = LoginRequest(username, password)
        return apiService.login(request)
    }

}
