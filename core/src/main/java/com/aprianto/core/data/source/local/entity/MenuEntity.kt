package com.aprianto.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu")
data class MenuEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "productId")
    var productId: String,

    @ColumnInfo(name = "productName")
    var productName: String,

    @ColumnInfo(name = "productCategory")
    var productCategory: String,

    @ColumnInfo(name = "productPrice")
    var productPrice: Int,

    @ColumnInfo(name = "productImage")
    var productImage: String,

    @ColumnInfo(name = "productDescription")
    var productDescription: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)