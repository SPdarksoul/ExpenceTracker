package com.task.expencetracker.data.model

import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.repo.AlertRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAlertRepository(dao: ExpenseDao): AlertRepository {
        return AlertRepository(dao)
    }
}
