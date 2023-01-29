package com.demo.minnies.shop.domain.usescases


import com.demo.minnies.database.models.Category
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCaseImpl
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shop.data.repos.ProductsRepo
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.util.toView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class FetchProductsByCategoryUseCase @Inject constructor(
    private val repo: ProductsRepo,
    private val getUserCurrencyPreferenceUseCaseImpl: GetUserCurrencyPreferenceUseCaseImpl
) {
    operator fun invoke(category: Category): Flow<List<ViewProduct>> {
        val items = repo.getItemsByCategory(category)
        val currency = getUserCurrencyPreferenceUseCaseImpl()
        val currencyFormattedItems = combine(items, currency) { products, curr ->
            products.map { item -> item.toView(Currency.valueOf(curr)) }
        }
        return currencyFormattedItems
    }
}