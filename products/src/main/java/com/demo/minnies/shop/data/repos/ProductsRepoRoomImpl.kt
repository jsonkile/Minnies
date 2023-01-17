package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.room.daos.ProductsDao
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.Product
import javax.inject.Inject

internal class ProductsRepoRoomImpl @Inject constructor(
    private val dao: ProductsDao
) : ProductsRepo {

    override suspend fun addItem(product: Product) = dao.insert(product)

    override fun getAllItems() = dao.getAll()

    override fun getItemsByCategory(category: Category) = dao.getItemsByCategory(category)

    override fun getFeaturedItems() = dao.getFeaturedItems()

    override fun getProductById(id: Int) = dao.getProduct(id)

    override fun searchProducts(term: String) = dao.searchProducts(term)
}