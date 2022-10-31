package com.demo.minnies.database.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.ShopItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: ShopItem): Long

    @Query("select * from shop_items")
    fun getAll(): Flow<List<ShopItem>>

    @Query("select * from shop_items where category = :category")
    fun getItemsByCategory(category: Category): Flow<List<ShopItem>>
}