package com.demo.minnies.orders.domain

import com.demo.minnies.orders.data.OrdersRepo
import com.demo.minnies.orders.presentation.models.ViewOrder
import com.demo.minnies.orders.util.toViewOrder
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shared.utils.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

interface FetchOrderUseCase {
    operator fun invoke(ref: String): Flow<ViewOrder>
}

class FetchOrderUseCaseImpl @Inject constructor(
    private val ordersRepo: OrdersRepo,
    private val getUserCurrencyPreferenceUseCase: GetUserCurrencyPreferenceUseCase
) :
    FetchOrderUseCase {
    override fun invoke(ref: String): Flow<ViewOrder> =
        combine(
            getUserCurrencyPreferenceUseCase(),
            ordersRepo.getOrder(ref).filterNotNull()
        ) { currency, order ->
            order.toViewOrder(Currency.valueOf(currency))
        }
}