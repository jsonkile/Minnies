package com.demo.minnies.shop.domain.usescases


import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.data.models.ShopItem
import com.demo.minnies.shop.data.repos.ShopRepo
import com.demo.minnies.shop.presentation.models.ViewShopItem
import com.demo.minnies.shop.util.forView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchAndGroupShopItemsUseCase @Inject constructor(private val repo: ShopRepo) {
    operator fun invoke(mock: Boolean): Flow<Map<Category, List<ViewShopItem>>> {
        val items = if (mock) flow { emit(mockList) } else repo.getAllItems()
        return items.map { list ->
            list.map { item -> item.forView() }.groupBy { it.category }
        }
    }

    companion object {
        val mockList = listOf(
            ShopItem(
                0,
                "Nike Shirt",
                "",
                "Very Comfortable",
                listOf(12, 13),
                Category.Top,
                0.5
            ),

            ShopItem(
                0,
                "Puma Shirt",
                "",
                "Very Beautiful",
                listOf(12, 13),
                Category.Top,
                0.7
            ),

            ShopItem(
                0,
                "Adidas Shirt",
                "",
                "Very Light",
                listOf(12, 13),
                Category.Top,
                0.2
            ),

            ShopItem(
                0,
                "Vans Retro",
                "",
                "Very Beautiful",
                listOf(41, 42),
                Category.Kicks,
                3.7
            ),

            ShopItem(
                0,
                "Clark Retro",
                "",
                "Very Light",
                listOf(45, 39),
                Category.Kicks,
                5.2
            )
        )
    }
}