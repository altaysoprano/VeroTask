package com.example.verotask.data.repository

import android.content.Context
import com.example.verotask.util.AccessTokenDataStore
import com.example.verotask.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class BaseRepositoryImp(private val accessTokenDataStore: AccessTokenDataStore) : BaseRepository {

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

                    accessTokenDataStore.saveAccessToken(oauth.getString("access_token"))
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