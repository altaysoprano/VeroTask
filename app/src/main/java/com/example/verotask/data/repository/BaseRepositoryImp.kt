package com.example.verotask.data.repository

import android.content.Context
import android.util.Log
import com.example.verotask.data.models.Task
import com.example.verotask.util.AccessTokenDataStore
import com.example.verotask.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

class BaseRepositoryImp(private val accessTokenDataStore: AccessTokenDataStore) : BaseRepository {

    override suspend fun login(username: String, password: String, onResult: (Resource<String>) -> Unit) {
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

    override suspend fun getTasks(onResult: (Resource<List<Task>>) -> Unit) {
        return withContext(Dispatchers.IO) {
            try {
                val accessToken = accessTokenDataStore.getAccessToken()

                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://api.baubuddy.de/dev/index.php/v1/tasks/select")
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val jsonArray = JSONArray(response.body()!!.string())
                    val tasks = parseTasks(jsonArray)

                    onResult(Resource.Success(tasks))
                } else {
                    onResult(Resource.Error(if(response.message() == "Unauthorized") response.message() else "Failed to fetch tasks"))
                }
            } catch (e: Exception) {
                onResult(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    private fun parseTasks(json: JSONArray): List<Task> {
        val tasksList = mutableListOf<Task>()

        for (i in 0 until json.length()) {
            val taskObj = json.getJSONObject(i)
            val task = taskObj.getString("task")
            val title = taskObj.getString("title")
            val description = taskObj.getString("description")
            val sort = taskObj.getString("sort")
            val wageType = taskObj.getString("wageType")
            val businessUnitKey = taskObj.getString("BusinessUnitKey")
            val businessUnit = taskObj.getString("businessUnit")
            val parentTaskID = taskObj.getString("parentTaskID")
            val preplanningBoardQuickSelect = taskObj.opt("preplanningBoardQuickSelect")
            val colorCode = taskObj.getString("colorCode")
            val workingTime = taskObj.opt("workingTime")
            val isAvailableInTimeTrackingKioskMode = taskObj.getBoolean("isAvailableInTimeTrackingKioskMode")

            val taskItem = Task(
                task,
                title,
                description,
                sort,
                wageType,
                businessUnitKey,
                businessUnit,
                parentTaskID,
                preplanningBoardQuickSelect,
                colorCode,
                workingTime,
                isAvailableInTimeTrackingKioskMode
            )
            tasksList.add(taskItem)
        }

        return tasksList
    }
}