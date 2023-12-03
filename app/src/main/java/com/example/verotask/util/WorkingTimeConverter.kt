package com.example.verotask.util

import androidx.room.TypeConverter

class WorkingTimeConverter {
    @TypeConverter
    fun fromString(value: String?): Any? {
        return value
    }

    @TypeConverter
    fun toString(value: Any?): String? {
        return value as? String
    }
}
