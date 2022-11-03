package com.demo.minnies.shop.data.repos

import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.data.models.ShopItem
import kotlinx.coroutines.flow.Flow

interface ShopRepo {

    suspend fun addItem(shopItem: ShopItem): Long

    fun getAllItems(): Flow<List<ShopItem>>

    fun getItemsByCategory(category: Category): Flow<List<ShopItem>>
}