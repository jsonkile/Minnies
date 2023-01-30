package com.demo.minnies.orders.domain

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.orders.data.OrdersRepo
import com.demo.minnies.orders.presentation.models.ViewOrder
import com.demo.minnies.orders.util.toViewOrder
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shared.utils.Currency
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface FetchUserOrdersUseCase {
    operator fun invoke(): Flow<List<ViewOrder>>
}

class FetchUserOrdersUseCaseImpl @Inject constructor(
    private val ordersRepo: OrdersRepo,
    private val getCachedUserUseCase: GetCachedUserUseCase,
    private val getUserCurrencyPreferenceUseCase: GetUserCurrencyPreferenceUseCase
) :
    FetchUserOrdersUseCase {
    override fun invoke(): Flow<List<ViewOrder>> =
        combine(getCachedUserUseCase(), getUserCurrencyPreferenceUseCase()) { user, currency ->
            UserAndCurrency(
                checkNotNull(user) {
                    "Please sign in to view your orders."
                }, Currency.valueOf(currency)
            )
        }.flatMapLatest { userAndCurrency ->
            ordersRepo.getUserOrders(userAndCurrency.user.id)
                .map { items -> items.map { it.toViewOrder(userAndCurrency.currency) } }
        }

    private data class UserAndCurrency(val user: PartialUser, val currency: Currency)
}