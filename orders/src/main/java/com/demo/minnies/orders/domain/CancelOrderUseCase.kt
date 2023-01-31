package com.demo.minnies.orders.domain

import com.demo.minnies.database.models.OrderIdAndStatus
import com.demo.minnies.database.models.OrderStatus
import com.demo.minnies.orders.data.OrdersRepo
import javax.inject.Inject

interface CancelOrderUseCase {
    suspend operator fun invoke(ref: String)
}

class CancelOrderUseCaseImpl @Inject constructor(private val repo: OrdersRepo) :
    CancelOrderUseCase {
    override suspend fun invoke(ref: String) {
        repo.updateOrder(OrderIdAndStatus(ref = ref, OrderStatus.Cancelled))
    }
}