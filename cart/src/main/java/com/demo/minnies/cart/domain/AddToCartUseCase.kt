package com.demo.minnies.cart.domain

import com.demo.minnies.cart.data.repos.CartRepo
import com.demo.minnies.database.room.models.CartItem
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val cartRepo: CartRepo) {
    operator fun invoke(productId: Int, quantity: Int) {
        cartRepo.addItem(CartItem(productId = productId, quantity = quantity))
    }
}