package com.example.verotask.data.repository

import com.example.verotask.data.models.LoginResponse
import com.example.verotask.network.ApiService

interface LoginRepository {
    suspend fun login(username: String, password: String): LoginResponse
}