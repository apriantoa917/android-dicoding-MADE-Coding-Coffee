package com.aprianto.core.domain.usecase

import androidx.lifecycle.LiveData
import com.aprianto.core.data.Resource
import com.aprianto.core.domain.model.Menu

interface MenuUseCase {
    fun getAllMenu(): LiveData<Resource<List<Menu>>>

    fun getFavoriteMenu(): LiveData<List<Menu>>

    fun setFavoriteMenu(menu: Menu, isFavorite:Boolean)
}