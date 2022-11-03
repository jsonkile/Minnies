package com.demo.minnies.shop.util

import com.demo.minnies.shop.data.models.ShopItem
import com.demo.minnies.shop.presentation.models.ViewShopItem

fun ShopItem.forView(): ViewShopItem {
    return ViewShopItem(
        name = name,
        description = description,
        price = "$$price",
        sizes = sizes,
        image = image,
        category = category
    )
}