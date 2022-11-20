package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.Product
import com.demo.minnies.shop.data.fakeProductsDataSets
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ShopRepoMockImpl @Inject constructor() : ShopRepo {

    private val items = fakeProductsDataSets.toMutableList()
    private val itemsAsFlow = MutableStateFlow(items.toList())

    override suspend fun addItem(product: Product): Long {
        items.add(product)
        itemsAsFlow.update { items }
        return items.indexOf(product).toLong()
    }

    override fun getAllItems(): Flow<List<Product>> = itemsAsFlow

    override fun getItemsByCategory(category: Category): Flow<List<Product>> {
        return itemsAsFlow.map { list -> list.filter { item -> item.category == category } }
    }

    override fun getFeaturedItems(): Flow<List<Product>> {
        return itemsAsFlow.map { list -> list.filter { item -> item.featured } }
    }

    override fun getProductById(id: Int): Flow<Product?> {
        return itemsAsFlow.map { it.find { product -> product.id == id } }
    }
}