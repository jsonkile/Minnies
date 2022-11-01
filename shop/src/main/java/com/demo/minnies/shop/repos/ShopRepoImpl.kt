package com.demo.minnies.shop.repos

import com.demo.minnies.database.room.daos.ShopDao
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.ShopItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShopRepoImpl @Inject constructor(
    private val dao: ShopDao
) : ShopRepo {

    override suspend fun addItem(shopItem: ShopItem) = dao.insert(shopItem)

    override fun getAllItems() = dao.getAll()

    override fun getItemsByCategory(category: Category) = dao.getItemsByCategory(category)
}