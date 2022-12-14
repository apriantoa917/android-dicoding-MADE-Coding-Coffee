package com.aprianto.core.domain.usecase

import com.aprianto.core.domain.model.Menu
import com.aprianto.core.domain.repository.IMenuRepository

class MenuInteractor(private val menuRepository: IMenuRepository) : MenuUseCase {
    override fun getAllMenu() = menuRepository.getAllMenu()

    override fun getFavoriteMenu() = menuRepository.getFavoriteMenu()

    override fun setFavoriteMenu(menu: Menu, isFavorite: Boolean) =
        menuRepository.setFavoriteMenu(menu, isFavorite)
}