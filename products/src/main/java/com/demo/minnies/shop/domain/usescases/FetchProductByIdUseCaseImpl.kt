package com.demo.minnies.shop.domain.usescases


import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.CustomExceptions
import com.demo.minnies.shop.data.repos.ProductsRepo
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.util.toView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface FetchProductByIdUseCase {
    operator fun invoke(id: Int): Flow<ViewProduct>
}

class FetchProductByIdUseCaseImpl @Inject constructor(
    private val repo: ProductsRepo,
    private val getUserCurrencyPreferenceUseCase: GetUserCurrencyPreferenceUseCase
) : FetchProductByIdUseCase {

    override operator fun invoke(id: Int): Flow<ViewProduct> {
        val item = repo.getProductById(id)
        val currency = getUserCurrencyPreferenceUseCase()
        val currencyFormattedItem = combine(item, currency) { product, curr ->
            product?.toView(Currency.valueOf(curr))
        }
        return currencyFormattedItem.onEach { result ->
            if (result == null) {
                throw CustomExceptions.NotFoundException("Sorry, but we couldn't find the product you requested for.")
            }
        }.map { it!! }
    }

}