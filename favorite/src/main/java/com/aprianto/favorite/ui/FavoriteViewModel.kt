package com.aprianto.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aprianto.core.domain.usecase.MenuUseCase

class FavoriteViewModel(menuUseCase: MenuUseCase) : ViewModel() {
    val menu = menuUseCase.getFavoriteMenu().asLiveData()
}