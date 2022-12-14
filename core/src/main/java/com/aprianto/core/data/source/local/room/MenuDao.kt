package com.aprianto.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aprianto.core.data.source.local.entity.MenuEntity

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu")
    fun getAllMenu(): LiveData<List<MenuEntity>>

    @Query("SELECT * FROM menu where isFavorite = 1")
    fun getFavoriteMenu(): LiveData<List<MenuEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMenu(menu: List<MenuEntity>)

    @Update
    fun updateFavoriteMenu(menu: MenuEntity)
}