package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.toFormattedPriceWithSign
import com.demo.minnies.shop.data.repos.CartRepo
import com.demo.minnies.shop.presentation.models.ViewCartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class FetchCartUseCaseMockImpl @Inject constructor(
    private val repo: CartRepo,
    private val getUserCurrencyPreferenceUseCase: GetUserCurrencyPreferenceUseCase,
    private val getCachedUserUseCase: GetCachedUserUseCase,
    private val fetchProductByIdUseCase: FetchProductByIdUseCase
) : FetchCartUseCase {
    override fun invoke(): Flow<List<ViewCartItem>> {
        val currencyFlow = getUserCurrencyPreferenceUseCase()

        val currencyFormattedItems = getCachedUserUseCase().flatMapLatest {
            checkNotNull(it) {
                "Please sign in to view your cart."
            }
            repo.getCart(it.id)
        }.combine(currencyFlow) { cart, curr ->
            cart.map { item ->
                val product = fetchProductByIdUseCase(item.productId).first()
                val currency = Currency.valueOf(curr)

                ViewCartItem(
                    id = item.id,
                    quantity = item.quantity,
                    productId = item.productId,
                    baseProductPrice = product.price,
                    productName = product.name,
                    productImage = product.image,
                    formattedProductPrice = product.price.toFormattedPriceWithSign(
                        Currency.valueOf(curr)
                    ),
                    formattedTotalAmount = (product.price * item.quantity).toFormattedPriceWithSign(
                        currency
                    ),
                    currency = currency
                )
            }
        }

        return currencyFormattedItems
    }
}