package com.demo.minnies.shop.presentation.models

import com.demo.minnies.database.models.Category
import com.demo.minnies.database.models.Product
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
