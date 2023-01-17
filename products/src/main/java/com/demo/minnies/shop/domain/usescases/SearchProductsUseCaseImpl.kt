package com.demo.minnies.shop.domain.usescases


import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCaseImpl
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shop.data.repos.ProductsRepo
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.presentation.models.toView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface SearchProductsUseCase {
    operator fun invoke(term: String): Flow<List<ViewProduct>>
}

class SearchProductsUseCaseImpl @Inject constructor(
    private val repo: ProductsRepo,
    private val getUserCurrencyPreferenceUseCaseImpl: GetUserCurrencyPreferenceUseCaseImpl
): SearchProductsUseCase {
    override operator fun invoke(term: String): Flow<List<ViewProduct>> {
        val items = repo.searchProducts(term)
        val currency = getUserCurrencyPreferenceUseCaseImpl()
        val currencyFormattedItems = combine(items, currency) { products, curr ->
            products.map { item -> item.toView(Currency.valueOf(curr)) }
        }
        return currencyFormattedItems
    }
}