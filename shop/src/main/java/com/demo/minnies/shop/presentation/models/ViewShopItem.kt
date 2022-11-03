package com.demo.minnies.shop.presentation.models

import com.demo.minnies.shop.data.models.Category

data class ViewShopItem(
    var name: String,
    var description: String,
    var price: String,
    var image: String,
    var sizes: List<Int>,
    var category: Category
)
