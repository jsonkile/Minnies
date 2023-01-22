package com.demo.minnies.orders.domain

import com.demo.minnies.orders.data.OrdersRepo
import com.demo.minnies.orders.presentation.models.ViewOrder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchUserOrdersUseCase {
    operator fun invoke(): Flow<List<ViewOrder>>
}

class FetchUserOrdersUseCaseImpl @Inject constructor(private val ordersRepo: OrdersRepo) :
    FetchUserOrdersUseCase {
    override fun invoke(): Flow<List<ViewOrder>> {

    }
}