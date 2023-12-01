package com.example.verotask.network

import com.example.verotask.data.repository.LoginRepository
import com.example.verotask.data.repository.LoginRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideTaskRepository(apiService: ApiService): LoginRepository {
        return LoginRepositoryImp(apiService)
    }
}
