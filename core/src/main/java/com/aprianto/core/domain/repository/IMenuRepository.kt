package com.aprianto.core.domain.repository

import com.aprianto.core.data.Resource
import com.aprianto.core.domain.model.Menu
import kotlinx.coroutines.flow.Flow

interface IMenuRepository {
    fun getAllMenu():Flow<Resource<List<Menu>>>

    fun getFavoriteMenu():Flow<List<Menu>>

    fun setFavoriteMenu(menu: Menu, isFavorite:Boolean)
}