package com.demo.minnies.cart.presentation.screens.models

import com.demo.minnies.database.models.CartItemDetail
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.toFormattedPriceWithSign

data class ViewCartItem(
    val id: Long,
    val quantity: Int,
    val productId: Int,
    val productName: String,
    val productImage: String,
    val currency: Currency = Currency.USD,
    // Product price in dollars
    val baseProductPrice: Double,
    // Product price in user's selected currency
    val convertedProductPrice: Double = baseProductPrice * currency.perDollar,
    val formattedProductPrice: String,
    val formattedTotalAmount: String
)

fun CartItemDetail.toView(userCurrencyPreference: Currency): ViewCartItem {
    return ViewCartItem(
        id = id,
        productId = productId,
        productName = productName,
        baseProductPrice = productPrice,
        quantity = quantity,
        formattedProductPrice = productPrice.toFormattedPriceWithSign(userCurrencyPreference),
        convertedProductPrice = productPrice * userCurrencyPreference.perDollar,
        productImage = productImage,
        formattedTotalAmount = (productPrice * quantity).toFormattedPriceWithSign(
            userCurrencyPreference
        ),
        currency = userCurrencyPreference
    )
}