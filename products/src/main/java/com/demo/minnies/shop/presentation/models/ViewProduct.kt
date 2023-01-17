package com.demo.minnies.shop.presentation.models

import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.Product
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.toFormattedPrice
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

data class ViewProduct(
    var id: Int = -1,
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var formattedPrice: String = "",
    var image: String = "",
    var sizes: List<Int> = emptyList(),
    var category: Category = Category.Top,
    var featured: Boolean = false,
    var rating: Double = 0.0
)

fun Product.toView(userCurrencyPreference: Currency): ViewProduct {

    return ViewProduct(
        name = name,
        description = description,
        formattedPrice = "${userCurrencyPreference.sign}${
            price.toFormattedPrice(userCurrencyPreference)
        }",
        price = price,
        sizes = sizes,
        image = image,
        category = category,
        featured = featured,
        rating = rating,
        id = id
    )
}
