package com.aprianto.core.ui.detail

import androidx.lifecycle.ViewModel
import com.aprianto.core.domain.model.Menu
import com.aprianto.core.domain.usecase.MenuUseCase

class DetailViewModel(private val menuUseCase: MenuUseCase) : ViewModel() {
    fun setFavoriteMenu(menu: Menu, newStatus: Boolean) =
        menuUseCase.setFavoriteMenu(menu, newStatus)
}