package com.example.verotask.data.repository

import com.example.verotask.data.models.Task
import com.example.verotask.util.Resource

interface BaseRepository {
    suspend fun login(username: String, password: String, onResult: (Resource<String>) -> Unit)

    suspend fun getTasks(onResult: (Resource<List<Task>>) -> Unit)

    suspend fun updateTasks(onResult: (Resource<List<Task>>) -> Unit)
}