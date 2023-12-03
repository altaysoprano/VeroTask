package com.example.verotask.util

import android.app.Activity
import android.content.Intent
import android.view.View
import com.example.verotask.data.local.LocalTask
import com.example.verotask.data.models.Task

fun<A: Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if(enabled) 1f else 0.5f
}

fun LocalTask.toTask(): Task {
    return Task(
        task = this.task,
        title = this.title,
        description = this.description,
        sort = this.sort,
        wageType = this.wageType,
        BusinessUnitKey = this.BusinessUnitKey,
        businessUnit = this.businessUnit,
        parentTaskID = this.parentTaskID,
        preplanningBoardQuickSelect = this.preplanningBoardQuickSelect,
        colorCode = this.colorCode,
        workingTime = this.workingTime,
        isAvailableInTimeTrackingKioskMode = this.isAvailableInTimeTrackingKioskMode
    )
}

fun Task.toLocalTask(): LocalTask {
    return LocalTask(
        task = this.task,
        title = this.title,
        description = this.description,
        sort = this.sort,
        wageType = this.wageType,
        BusinessUnitKey = this.BusinessUnitKey,
        businessUnit = this.businessUnit,
        parentTaskID = this.parentTaskID,
        preplanningBoardQuickSelect = this.preplanningBoardQuickSelect,
        colorCode = this.colorCode,
        workingTime = this.workingTime,
        isAvailableInTimeTrackingKioskMode = this.isAvailableInTimeTrackingKioskMode
    )
}
