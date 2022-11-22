package com.demo.minnies.shop.domain.usescases


import com.demo.minnies.database.room.models.Category
import com.demo.minnies.shop.data.repos.ShopRepo
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.presentation.models.toView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchShopItemsByCategoriesUseCase @Inject constructor(private val repo: ShopRepo) {
    operator fun invoke(): Flow<Map<Category, List<ViewProduct>>> {
        val items = repo.getAllItems()
        return items.map { list ->
            list.map { item -> item.toView() }.groupBy { it.category }
        }
    }
}