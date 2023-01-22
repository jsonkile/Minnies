package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.database.models.CartItem
import com.demo.minnies.shop.data.repos.ProductsRepo
import javax.inject.Inject


interface AddToCartUseCase {
    suspend operator fun invoke(productId: Int, quantity: Int, userId: Long?): Long
}

class AddToCartUseCaseImpl @Inject constructor(private val productsRepo: ProductsRepo) : AddToCartUseCase {
    override suspend operator fun invoke(productId: Int, quantity: Int, userId: Long?): Long {

        checkNotNull(userId) { "Please sign in." }

        check(!productsRepo.checkAddedToCart(productId, userId)) {
            "The item is already in your cart."
        }

        return productsRepo.addToCart(
            CartItem(productId = productId, quantity = quantity, userId = userId)
        )

    }
}