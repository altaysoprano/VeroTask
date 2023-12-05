package com.example.verotask.util

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.verotask.data.repository.BaseRepository
import javax.inject.Inject

class MyWorkerFactory @Inject constructor(
    private val repository: BaseRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            RefreshWorker::class.java.name ->
                RefreshWorker(repository, appContext, workerParameters)
            else -> null
        }
    }
}
