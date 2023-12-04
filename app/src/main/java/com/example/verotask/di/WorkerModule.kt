package com.example.verotask.di

import android.content.Context
import androidx.work.Constraints
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.verotask.data.repository.BaseRepository
import com.example.verotask.util.RefreshWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Provides
    @Singleton
    fun provideRefreshWorker(
        @ApplicationContext context: Context,
        workerParams: WorkerParameters
    ): RefreshWorker {
        return RefreshWorker(context, workerParams)
    }
}