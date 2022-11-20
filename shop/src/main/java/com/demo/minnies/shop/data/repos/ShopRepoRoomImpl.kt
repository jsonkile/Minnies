package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.room.daos.ShopDao
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.Product
import javax.inject.Inject

class ShopRepoRoomImpl @Inject constructor(
    private val dao: ShopDao
) : ShopRepo {

    override suspend fun addItem(product: Product) = dao.insert(product)

    override fun getAllItems() = dao.getAll()

    override fun getItemsByCategory(category: Category) = dao.getItemsByCategory(category)

    override fun getFeaturedItems() = dao.getFeaturedItems()

    override fun getProductById(id: Int) = dao.getProduct(id)
}