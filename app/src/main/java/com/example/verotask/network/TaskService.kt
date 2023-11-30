package com.example.verotask.network

import com.example.verotask.data.models.Task
import retrofit2.http.GET

interface TaskService {
    @GET("v1/tasks/select")
    suspend fun getTasks(): List<Task>
}
