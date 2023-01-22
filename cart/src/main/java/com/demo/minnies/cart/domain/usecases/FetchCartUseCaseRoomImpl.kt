package com.demo.minnies.cart.domain.usecases


import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.cart.data.repos.CartRepo
import com.demo.minnies.cart.presentation.screens.models.ViewCartItem
import com.demo.minnies.cart.presentation.screens.models.toView
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shared.utils.Currency
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class FetchCartUseCaseRoomImpl @Inject constructor(
    private val repo: CartRepo,
    private val getUserCurrencyPreferenceUseCase: GetUserCurrencyPreferenceUseCase,
    private val getCachedUserUseCase: GetCachedUserUseCase
) : FetchCartUseCase {
    override operator fun invoke(): Flow<List<ViewCartItem>> {
        val currency = getUserCurrencyPreferenceUseCase()

        val currencyFormattedItems = getCachedUserUseCase().flatMapLatest {
            checkNotNull(it) {
                "Please sign in to view your cart."
            }
            repo.getCartWithDetails(it.id)
        }.combine(currency) { cart, curr ->
            cart.map { item -> item.toView(Currency.valueOf(curr)) }
        }

        return currencyFormattedItems
    }
}