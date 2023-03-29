package com.demo.minnies.shop.domain.usescases


import com.demo.minnies.database.models.ProductCategory
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.util.toView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface FetchProductsByCategoriesUseCase {
    operator fun invoke(): Flow<Map<ProductCategory, List<ViewProduct>>>
}

class FetchProductsByCategoriesUseCaseImpl @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getUserCurrencyPreferenceUseCase: GetUserCurrencyPreferenceUseCase
) : FetchProductsByCategoriesUseCase {
    override operator fun invoke(): Flow<Map<ProductCategory, List<ViewProduct>>> {
        val items = getAllProductsUseCase()
        val currency = getUserCurrencyPreferenceUseCase()
        val currencyFormattedItems = combine(items, currency) { products, curr ->
            products.map { item -> item.toView(Currency.valueOf(curr)) }.groupBy { it.productCategory }
        }
        return currencyFormattedItems
    }
}