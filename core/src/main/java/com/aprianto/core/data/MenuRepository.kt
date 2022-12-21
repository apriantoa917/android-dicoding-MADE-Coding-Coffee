package com.aprianto.core.data

import com.aprianto.core.data.source.local.LocalDataSource
import com.aprianto.core.data.source.remote.RemoteDataSource
import com.aprianto.core.data.source.remote.network.ApiResponse
import com.aprianto.core.data.source.remote.response.MenuResponse
import com.aprianto.core.domain.model.Menu
import com.aprianto.core.domain.repository.IMenuRepository
import com.aprianto.core.utils.AppExecutors
import com.aprianto.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MenuRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMenuRepository {

    companion object {
        @Volatile
        private var instance: MenuRepository? = null
    }

    override fun getAllMenu(): Flow<Resource<List<Menu>>> =
        object : NetworkBoundResource<List<Menu>, List<MenuResponse>>() {
            override fun loadFromDB(): Flow<List<Menu>> {
                return localDataSource.getAllMenu().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Menu>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MenuResponse>>> =
                remoteDataSource.getAllMenu()

            override suspend fun saveCallResult(data: List<MenuResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMenu(tourismList)
            }
        }.asFlow()

    override fun getFavoriteMenu(): Flow<List<Menu>> {
        return localDataSource.getFavoriteMenu().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteMenu(menu: Menu, isFavorite: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(menu)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMenu(tourismEntity, isFavorite) }
    }

}