package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.Product
import kotlinx.coroutines.flow.Flow

interface ShopRepo {

    suspend fun addItem(product: Product): Long

    fun getAllItems(): Flow<List<Product>>

    fun getItemsByCategory(category: Category): Flow<List<Product>>

    fun getFeaturedItems(): Flow<List<Product>>

    fun getProductById(id: Int): Flow<Product?>
}