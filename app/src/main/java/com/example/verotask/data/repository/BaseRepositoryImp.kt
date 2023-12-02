package com.example.verotask.data.repository

import android.util.Log
import com.example.verotask.data.models.LoginRequest
import com.example.verotask.data.models.LoginResponse
import com.example.verotask.network.AuthApi
import com.example.verotask.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BaseRepositoryImp(private val authApi: AuthApi): BaseRepository {

    override suspend fun getAccessToken(username: String, password: String, onResult: (Resource<String>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val mediaType = MediaType.parse("application/json")
                val body = RequestBody.create(mediaType, "{\"username\":\"$username\",\"password\":\"$password\"}")
                val request = Request.Builder()
                    .url("https://api.baubuddy.de/index.php/login")
                    .post(body)
                    .addHeader("Authorization", "Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz")
                    .addHeader("Content-Type", "application/json")
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val json = JSONObject(response.body()!!.string())
                    val oauth = json.getJSONObject("oauth")
                    onResult(Resource.Success(oauth.getString("access_token")))
                } else {
                    onResult(Resource.Error("Login failed"))
                }
            } catch (e: Exception) {
                onResult(Resource.Error(e.message!!))
            }
        }
    }
}