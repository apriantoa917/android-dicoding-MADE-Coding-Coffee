package com.aprianto.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu(
    val productId : String,
    val productName : String,
    val productCategory: String,
    val productPrice : Int,
    val productImage : String,
    val productDescription : String,
    val isFavorite: Boolean
) : Parcelable
