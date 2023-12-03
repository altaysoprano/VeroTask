package com.example.verotask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTasks(tasks: List<LocalTask>)

    @Query("SELECT * FROM tasks")
    fun getTasks(): List<LocalTask>
}
