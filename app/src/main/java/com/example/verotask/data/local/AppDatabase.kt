package com.example.verotask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.verotask.util.AnyTypeConverter

@Database(entities = [LocalTask::class], version = 1, exportSchema = false)
@TypeConverters(AnyTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
