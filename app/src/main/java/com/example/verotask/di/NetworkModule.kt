package com.example.verotask.di

import android.content.Context
import com.example.verotask.data.local.AppDatabase
import com.example.verotask.data.repository.BaseRepository
import com.example.verotask.data.repository.BaseRepositoryImp
import com.example.verotask.util.AccessTokenDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAccessTokenManager(@ApplicationContext context: Context): AccessTokenDataStore {
        return AccessTokenDataStore(context)
    }

    @Singleton
    @Provides
    fun provideBaseRepository(accessTokenDataStore: AccessTokenDataStore, appDatabase: AppDatabase): BaseRepository {
        return BaseRepositoryImp(accessTokenDataStore, appDatabase)
    }
}
