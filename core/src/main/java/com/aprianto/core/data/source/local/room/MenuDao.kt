package com.aprianto.core.data.source.local.room

import androidx.room.*
import com.aprianto.core.data.source.local.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu")
    fun getAllMenu(): Flow<List<MenuEntity>>

    @Query("SELECT * FROM menu where isFavorite = 1")
    fun getFavoriteMenu(): Flow<List<MenuEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: List<MenuEntity>)

    @Update
    fun updateFavoriteMenu(menu: MenuEntity)
}