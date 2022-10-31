package com.demo.minnies.shop.repos

import com.demo.minnies.database.room.daos.ShopDao
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.ShopItem
import kotlinx.coroutines.flow.Flow

class ShopRepoImpl(private val dao: ShopDao) : ShopRepo {

    override fun getAllItems() = dao.getAll()

    override fun getItemsByCategory(category: Category): Flow<List<ShopItem>> {
        TODO("Not yet implemented")
    }
}