package com.demo.minnies.shop.repos

import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.ShopItem
import kotlinx.coroutines.flow.Flow

interface ShopRepo {

    fun getAllItems(): Flow<List<ShopItem>>

    fun getItemsByCategory(category: Category): Flow<List<ShopItem>>
}