package com.demo.minnies.orders.domain

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.database.models.Order
import com.demo.minnies.orders.data.OrdersRepo
import com.demo.minnies.shared.utils.SIGN_IN_MESSAGE
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface MakeOrderUseCase {
    suspend operator fun invoke(cartItemsIds: List<Long>): Long
}

class MakeOrderUseCaseImpl @Inject constructor(
    private val ordersRepo: OrdersRepo,
    private val getCachedUserUseCase: GetCachedUserUseCase
) :
    MakeOrderUseCase {
    override suspend fun invoke(cartItemsIds: List<Long>): Long {
        val user = checkNotNull(getCachedUserUseCase().first()) { SIGN_IN_MESSAGE }

        return ordersRepo.addOrder(Order(cartItems = cartItemsIds, user = user.id))
    }

}