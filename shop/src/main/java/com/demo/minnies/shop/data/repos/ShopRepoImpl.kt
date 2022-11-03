package com.demo.minnies.shop.data.repos

import com.demo.minnies.shop.data.local.daos.ShopDao
import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.data.models.ShopItem
import javax.inject.Inject

class ShopRepoImpl @Inject constructor(
    private val dao: ShopDao
) : ShopRepo {

    override suspend fun addItem(shopItem: ShopItem) = dao.insert(shopItem)

    override fun getAllItems() = dao.getAll()

    override fun getItemsByCategory(category: Category) = dao.getItemsByCategory(category)
}