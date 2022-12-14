package com.aprianto.core.data

import androidx.annotation.MenuRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.aprianto.core.data.source.local.LocalDataSource
import com.aprianto.core.data.source.remote.RemoteDataSource
import com.aprianto.core.data.source.remote.network.ApiResponse
import com.aprianto.core.data.source.remote.response.MenuResponse
import com.aprianto.core.domain.model.Menu
import com.aprianto.core.domain.repository.IMenuRepository
import com.aprianto.core.utils.AppExecutors
import com.aprianto.core.utils.DataMapper

class MenuRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMenuRepository {

    companion object {
        @Volatile
        private var instance: MenuRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MenuRepository =
            instance ?: synchronized(this) {
                instance ?: MenuRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllMenu(): LiveData<Resource<List<Menu>>> =
        object : NetworkBoundResource<List<Menu>, List<MenuResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Menu>> {
                return Transformations.map(localDataSource.getAllMenu()) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Menu>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override fun createCall(): LiveData<ApiResponse<List<MenuResponse>>> =
                remoteDataSource.getAllMenu()

            override fun saveCallResult(data: List<MenuResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMenu(tourismList)
            }
        }.asLiveData()

    override fun getFavoriteMenu(): LiveData<List<Menu>> {
        return Transformations.map(localDataSource.getFavoriteMenu()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMenu(menu: Menu, isFavorite: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(menu)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMenu(tourismEntity, isFavorite) }
    }

}