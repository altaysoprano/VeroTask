package com.example.verotask.data.models

data class Task(
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
