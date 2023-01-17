package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.database.room.models.CartItem
import com.demo.minnies.shop.data.repos.CartRepo
import javax.inject.Inject


interface AddToCartUseCase {
    suspend operator fun invoke(productId: Int, quantity: Int, userId: Long?): Long
}

class AddToCartUseCaseImpl @Inject constructor(private val cartRepo: CartRepo) : AddToCartUseCase {
    override suspend operator fun invoke(productId: Int, quantity: Int, userId: Long?): Long {

        checkNotNull(userId) { "Please sign in." }

        check(cartRepo.getItemByProductAndUser(productId, userId) == null) {
            "The item is already in your cart."
        }

        return cartRepo.addItem(
            CartItem(productId = productId, quantity = quantity, userId = userId)
        )

    }
}