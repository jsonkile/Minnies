package com.demo.minnies.orders.domain

import com.demo.minnies.database.models.OrderIdAndStatus
import com.demo.minnies.database.models.OrderStatus
import com.demo.minnies.orders.data.OrdersRepo
import javax.inject.Inject

interface ConfirmOrderUseCase {
    suspend operator fun invoke(ref: String)
}

class ConfirmOrderUseCaseImpl @Inject constructor(private val repo: OrdersRepo) :
    ConfirmOrderUseCase {
    override suspend fun invoke(ref: String) {
        repo.updateOrder(OrderIdAndStatus(ref = ref, OrderStatus.Completed))
    }
}