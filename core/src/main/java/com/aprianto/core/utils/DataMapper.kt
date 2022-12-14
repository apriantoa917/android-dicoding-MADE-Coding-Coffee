package com.aprianto.core.utils

import com.aprianto.core.data.source.local.entity.MenuEntity
import com.aprianto.core.data.source.remote.response.MenuResponse
import com.aprianto.core.domain.model.Menu

object DataMapper {
    fun mapResponsesToEntities(input: List<MenuResponse>): List<MenuEntity> {
        val menuList = ArrayList<MenuEntity>()
        input.map {
            val menu = MenuEntity(
                productId = it.productId,
                productName = it.productName,
                productCategory = it.productCategory,
                productPrice = it.productPrice,
                productImage = it.productImage,
                productDescription = it.productDescription,
                isFavorite = false
            )
            menuList.add(menu)
        }
        return menuList
    }

    fun mapEntitiesToDomain(input: List<MenuEntity>): List<Menu> =
        input.map {
            Menu(
                productId = it.productId,
                productName = it.productName,
                productCategory = it.productCategory,
                productPrice = it.productPrice,
                productImage = it.productImage,
                productDescription = it.productDescription,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Menu) = MenuEntity(
        productId = input.productId,
        productName = input.productName,
        productCategory = input.productCategory,
        productPrice = input.productPrice,
        productImage = input.productImage,
        productDescription = input.productDescription,
        isFavorite = input.isFavorite
    )
}