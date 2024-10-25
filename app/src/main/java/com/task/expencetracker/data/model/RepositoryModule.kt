package com.task.expencetracker.data.model



import com.task.expencetracker.data.repo.AlertRepository
import com.task.expencetracker.data.repo.AlertRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAlertRepository(): AlertRepository {
        return AlertRepositoryImpl() // Provide the implementation here
    }
}