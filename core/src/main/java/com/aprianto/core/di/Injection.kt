package com.aprianto.core.di

import android.content.Context
import com.aprianto.core.data.MenuRepository
import com.aprianto.core.data.source.local.LocalDataSource
import com.aprianto.core.data.source.local.room.MenuDatabase
import com.aprianto.core.data.source.remote.RemoteDataSource
import com.aprianto.core.data.source.remote.network.ApiConfig
import com.aprianto.core.domain.repository.IMenuRepository
import com.aprianto.core.domain.usecase.MenuInteractor
import com.aprianto.core.domain.usecase.MenuUseCase
import com.aprianto.core.utils.AppExecutors

object Injection {
    private fun provideRepository(context: Context): IMenuRepository {
        val database = MenuDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.menuDao())
        val appExecutors = AppExecutors()

        return MenuRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideMenuUseCase(context: Context): MenuUseCase {
        val repository = provideRepository(context)
        return MenuInteractor(repository)
    }
}