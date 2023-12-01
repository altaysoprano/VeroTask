package com.example.verotask.data.repository

import com.example.verotask.util.Resource

interface BaseRepository {

    suspend fun login(username: String, password: String, onResult: (Resource<String>) -> Unit)
}