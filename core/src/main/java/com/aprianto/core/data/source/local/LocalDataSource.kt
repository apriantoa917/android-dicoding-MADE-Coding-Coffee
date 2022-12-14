package com.aprianto.core.data.source.local

import androidx.lifecycle.LiveData
import com.aprianto.core.data.source.local.entity.MenuEntity
import com.aprianto.core.data.source.local.room.MenuDao

class LocalDataSource private constructor(private val menuDao: MenuDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(menuDao: MenuDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(menuDao)
            }
    }

    fun getAllMenu(): LiveData<List<MenuEntity>> = menuDao.getAllMenu()

    fun getFavoriteMenu(): LiveData<List<MenuEntity>> = menuDao.getFavoriteMenu()

    fun insertMenu(menuList: List<MenuEntity>) = menuDao.insertMenu(menuList)

    fun setFavoriteMenu(menu: MenuEntity, newState: Boolean) {
        menu.isFavorite = newState
        menuDao.updateFavoriteMenu(menu)
    }
}