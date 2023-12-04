package com.example.verotask.util

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.verotask.data.repository.BaseRepository
import com.example.verotask.data.repository.BaseRepositoryImp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RefreshWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("Mesaj: ", "dowork başladı")
        return try {
            Log.d("Mesaj: ", "Veriler güncellendi")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
