package com.example.verotask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.verotask.R
import com.example.verotask.util.RefreshWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init(force = false)
    }

    private fun init(force: Boolean) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val refreshWorkerRequest = PeriodicWorkRequestBuilder<RefreshWorker>(
            repeatInterval = 60,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "refreshWorker",
            if (force) androidx.work.ExistingPeriodicWorkPolicy.REPLACE else androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            refreshWorkerRequest
        )
    }
}
