package com.demo.minnies.shop.presentation.models

import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.data.models.Product
import java.math.BigDecimal
import java.math.RoundingMode

data class ViewProduct(
    var id: Int = -1,
    var name: String = "",
    var description: String = "",
    var price: String = "",
    var image: String = "",
    var sizes: List<Int> = emptyList(),
    var category: Category = Category.Top,
    var featured: Boolean = false,
    var rating: Double = 0.0
)


fun Product.toView(): ViewProduct {

    val formattedPrice = BigDecimal(price).setScale(2, RoundingMode.HALF_DOWN)

    return ViewProduct(
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
