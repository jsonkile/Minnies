package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.models.CartItem
import com.demo.minnies.database.models.Category
import com.demo.minnies.database.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepo {
    suspend fun addItem(product: Product): Long

    suspend fun addItems(products : List<Product>) : List<Long>

    fun getAllItems(): Flow<List<Product>>

    fun countAllItems(): Flow<Int>

    fun getItemsByCategory(category: Category): Flow<List<Product>>

    fun getFeaturedItems(): Flow<List<Product>>

    fun getProductById(id: Int): Flow<Product?>

    fun searchProducts(term: String): Flow<List<Product>>

    suspend fun addToCart(cartItem: CartItem) : Long

    suspend fun checkAddedToCart(productId: Int, userId: Long) : Boolean
}