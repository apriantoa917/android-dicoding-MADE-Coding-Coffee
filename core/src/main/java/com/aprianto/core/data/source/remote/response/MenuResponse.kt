package com.aprianto.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @field:SerializedName("product_id")
    val productId: String,

    @field:SerializedName("product_name")
    var productName: String,

    @field:SerializedName("product_category")
    var productCategory: String,

    @field:SerializedName("product_price")
    var productPrice: Int,

    @field:SerializedName("product_image")
    var productImage: String,

    @field:SerializedName("product_description")
    var productDescription: String,
)