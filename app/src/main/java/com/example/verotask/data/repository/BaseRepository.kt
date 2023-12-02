package com.example.verotask.data.repository

import com.example.verotask.util.Resource

interface BaseRepository {
    suspend fun getAccessToken(
        username: String,
        password: String,
        onResult: (Resource<String>) -> Unit
    )
}