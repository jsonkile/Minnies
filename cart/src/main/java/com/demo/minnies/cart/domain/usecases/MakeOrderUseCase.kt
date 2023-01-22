package com.demo.minnies.cart.domain.usecases

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.cart.data.repos.CartRepo
import com.demo.minnies.database.models.CartItemIdAndStatus
import com.demo.minnies.database.models.CartStatus
import com.demo.minnies.database.models.Order
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface MakeOrderUseCase {
    suspend operator fun invoke(cartItemsIds: List<Long>): Long
}

class MakeOrderUseCaseImpl @Inject constructor(
    private val cartRepo: CartRepo,
    private val getCachedUserUseCase: GetCachedUserUseCase
) :
    MakeOrderUseCase {
    override suspend fun invoke(cartItemsIds: List<Long>): Long {
        val user = checkNotNull(getCachedUserUseCase().first()) {
            "Please sign in."
        }

        cartRepo.updateItems(cartItemsIds.map {
            CartItemIdAndStatus(id = it, status = CartStatus.Closed)
        })

        return cartRepo.makeOrder(Order(cartItems = cartItemsIds, user = user.id))
    }

}