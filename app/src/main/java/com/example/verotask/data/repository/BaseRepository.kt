package com.example.verotask.data.repository

import com.example.verotask.data.models.LoginRequest
import com.example.verotask.data.models.LoginResponse
import com.example.verotask.network.AuthApi
import com.example.verotask.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BaseRepository(private val authApi: AuthApi) {

    suspend fun login(username: String, password: String, onResult: (Resource<String>) -> Unit) {

        onResult(Resource.Loading())

        val loginCall = authApi.login(LoginRequest(username, password))
        loginCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val accessToken = response.body()?.oauth?.access_token
                    accessToken?.let {accessToken ->
                        onResult(Resource.Success(accessToken))
                    } ?: run {
                        onResult(Resource.Error("Access token is null"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody ?: "Error occurred"
                    onResult(Resource.Error(errorMessage))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onResult(Resource.Error("Network error occurred"))
            }
        })
    }

}