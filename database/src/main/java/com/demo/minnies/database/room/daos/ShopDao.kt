package com.demo.minnies.database.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Product): Long

    @Query("select * from products")
    fun getAll(): Flow<List<Product>>

    @Query("select * from products where category = :category")
    fun getItemsByCategory(category: Category): Flow<List<Product>>

    @Query("select * from products where featured == 1")
    fun getFeaturedItems(): Flow<List<Product>>

    @Query("select * from products where id == :id")
    fun getProduct(id: Int): Flow<Product?>
}