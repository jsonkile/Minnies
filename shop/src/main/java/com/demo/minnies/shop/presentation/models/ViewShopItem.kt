package com.demo.minnies.shop.presentation.models

import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.data.models.ShopItem
import java.math.BigDecimal
import java.math.RoundingMode

data class ViewShopItem(
    var id: Int,
    var name: String,
    var description: String,
    var price: String,
    var image: String,
    var sizes: List<Int>,
    var category: Category,
    var featured: Boolean = false,
    var rating: Double = 0.0
)


fun ShopItem.toView(): ViewShopItem {

    val formattedPrice = BigDecimal(price).setScale(2, RoundingMode.HALF_DOWN)

    return ViewShopItem(
        name = name,
        description = description,
        price = "$$formattedPrice",
        sizes = sizes,
        image = image,
        category = category,
        featured = featured,
        rating = rating,
        id = id
    )
}
