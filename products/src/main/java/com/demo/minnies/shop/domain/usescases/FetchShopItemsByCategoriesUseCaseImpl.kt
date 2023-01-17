package com.demo.minnies.shop.domain.usescases


import com.demo.minnies.database.room.models.Category
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCaseImpl
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shop.data.repos.ProductsRepo
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.presentation.models.toView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface FetchShopItemsByCategoriesUseCase {
    operator fun invoke(): Flow<Map<Category, List<ViewProduct>>>
}

class FetchShopItemsByCategoriesUseCaseImpl @Inject constructor(
    private val repo: ProductsRepo,
    private val getUserCurrencyPreferenceUseCaseImpl: GetUserCurrencyPreferenceUseCaseImpl
) : FetchShopItemsByCategoriesUseCase {
    override operator fun invoke(): Flow<Map<Category, List<ViewProduct>>> {
        val items = repo.getAllItems()
        val currency = getUserCurrencyPreferenceUseCaseImpl()
        val currencyFormattedItems = combine(items, currency) { products, curr ->
            products.map { item -> item.toView(Currency.valueOf(curr)) }.groupBy { it.category }
        }
        return currencyFormattedItems
    }
}