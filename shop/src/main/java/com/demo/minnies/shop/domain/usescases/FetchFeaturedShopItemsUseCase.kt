package com.demo.minnies.shop.domain.usescases


import com.demo.minnies.shop.data.repos.ShopRepo
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.presentation.models.toView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchFeaturedShopItemsUseCase @Inject constructor(private val repo: ShopRepo) {
    operator fun invoke(): Flow<List<ViewProduct>> {
        val items = repo.getFeaturedItems()
        return items.map { list -> list.map { item -> item.toView() } }
    }
}