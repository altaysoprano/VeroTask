package com.example.verotask.util

import androidx.room.TypeConverter
import com.google.gson.Gson

class AnyTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): Any? {
        return gson.fromJson(value, Any::class.java)
    }

    @TypeConverter
    fun toString(value: Any?): String? {
        return gson.toJson(value)
    }
}
