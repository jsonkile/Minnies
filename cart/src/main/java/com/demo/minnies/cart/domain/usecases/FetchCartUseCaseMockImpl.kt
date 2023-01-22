package com.demo.minnies.cart.domain.usecases

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.cart.data.repos.CartRepo
import com.demo.minnies.cart.presentation.screens.models.ViewCartItem
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.toFormattedPriceWithSign
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class FetchCartUseCaseMockImpl @Inject constructor(
    private val repo: CartRepo,
    private val getUserCurrencyPreferenceUseCase: GetUserCurrencyPreferenceUseCase,
    private val getCachedUserUseCase: GetCachedUserUseCase
) : FetchCartUseCase {
    override fun invoke(): Flow<List<ViewCartItem>> {
        val currencyFlow = getUserCurrencyPreferenceUseCase()

        val currencyFormattedItems = getCachedUserUseCase().flatMapLatest {
            checkNotNull(it) {
                "Please sign in to view your cart."
            }
            repo.getCartWithDetails(it.id)
        }.combine(currencyFlow) { cart, curr ->

            cart.map { item ->
                val currency = Currency.valueOf(curr)

                ViewCartItem(
                    id = item.id,
                    quantity = item.quantity,
                    productId = item.productId,
                    baseProductPrice = item.productPrice,
                    productName = item.productName,
                    productImage = item.productImage,
                    formattedProductPrice = item.productPrice.toFormattedPriceWithSign(
                        Currency.valueOf(curr)
                    ),
                    formattedTotalAmount = (item.productPrice * item.quantity).toFormattedPriceWithSign(
                        currency
                    ),
                    currency = currency
                )
            }

        }

        return currencyFormattedItems
    }
}