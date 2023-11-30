package com.example.verotask.data.models

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("task") val task: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("colorCode") val colorCode: String
)
