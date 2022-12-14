package com.aprianto.core.domain.repository

import android.content.res.Resources
import androidx.lifecycle.LiveData
import com.aprianto.core.data.Resource
import com.aprianto.core.domain.model.Menu

interface IMenuRepository {
    fun getAllMenu():LiveData<Resource<List<Menu>>>

    fun getFavoriteMenu():LiveData<List<Menu>>

    fun setFavoriteMenu(menu: Menu, isFavorite:Boolean)
}