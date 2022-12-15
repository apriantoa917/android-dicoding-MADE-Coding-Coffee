package com.aprianto.core.data.source.local

import androidx.lifecycle.LiveData
import com.aprianto.core.data.source.local.entity.MenuEntity
import com.aprianto.core.data.source.local.room.MenuDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val menuDao: MenuDao) {

    companion object {
        private var instance: LocalDataSource? = null
    }

    fun getAllMenu(): Flow<List<MenuEntity>> = menuDao.getAllMenu()

    fun getFavoriteMenu(): Flow<List<MenuEntity>> = menuDao.getFavoriteMenu()

    suspend fun insertMenu(menuList: List<MenuEntity>) = menuDao.insertMenu(menuList)

    fun setFavoriteMenu(menu: MenuEntity, newState: Boolean) {
        menu.isFavorite = newState
        menuDao.updateFavoriteMenu(menu)
    }
}