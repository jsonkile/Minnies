package com.demo.minnies.shop.presentation.models

import com.demo.minnies.database.models.ProductCategory

data class ViewProduct(
    var id: Int = -1,
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var formattedPrice: String = "",
    var image: String = "",
    var sizes: List<Int> = emptyList(),
    var productCategory: ProductCategory = ProductCategory.Top,
    var featured: Boolean = false,
    var rating: Double = 0.0
)
