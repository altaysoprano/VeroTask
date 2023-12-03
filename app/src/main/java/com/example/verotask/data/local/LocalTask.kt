package com.example.verotask.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.verotask.data.models.Task

@Entity(tableName = "tasks")
data class LocalTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val task: String,
    val title: String,
    val description: String,
    val sort: String,
    val wageType: String,
    val BusinessUnitKey: String,
    val businessUnit: String,
    val parentTaskID: String?,
    val preplanningBoardQuickSelect: Any?,
    val colorCode: String,
    val workingTime: Any?,
    val isAvailableInTimeTrackingKioskMode: Boolean
)
