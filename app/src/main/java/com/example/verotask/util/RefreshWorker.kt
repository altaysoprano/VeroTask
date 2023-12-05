package com.example.verotask.util

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.verotask.data.repository.BaseRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.runBlocking

@HiltWorker
class RefreshWorker @AssistedInject constructor(
    private val repository: BaseRepository,
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            runBlocking {
                repository.updateTasks {}
            }
            Log.d("Mesaj: ", "Updated")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
