package com.demo.minnies.shop.data.repos

import com.demo.minnies.shop.data.fakeShopItemsDataSet
import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.data.models.ShopItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ShopRepoMockImpl @Inject constructor() : ShopRepo {

    private val items = fakeShopItemsDataSet.toMutableList()
    private val itemsAsFlow = MutableStateFlow(items.toList())

    override suspend fun addItem(shopItem: ShopItem): Long {
        items.add(shopItem)
        itemsAsFlow.update { items }
        return items.indexOf(shopItem).toLong()
    }

    override fun getAllItems(): Flow<List<ShopItem>> = itemsAsFlow

    override fun getItemsByCategory(category: Category): Flow<List<ShopItem>> {
        return itemsAsFlow.map { list -> list.filter { item -> item.category == category } }
    }

    override fun getFeaturedItems(): Flow<List<ShopItem>> {
        return itemsAsFlow.map { list -> list.filter { item -> item.featured } }
    }
}