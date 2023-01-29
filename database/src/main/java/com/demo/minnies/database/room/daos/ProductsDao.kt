package com.demo.minnies.database.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.minnies.database.models.Category
import com.demo.minnies.database.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Product): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(items: List<Product>): List<Long>

    @Query("select * from products")
    fun getAll(): Flow<List<Product>>

    @Query("select * from products where category = :category")
    fun getItemsByCategory(category: Category): Flow<List<Product>>

    @Query("select * from products where featured == 1")
    fun getFeaturedItems(): Flow<List<Product>>

    @Query("select * from products where id == :id")
    fun get(id: Int): Flow<Product?>

    @Query("select * from products where name like :term")
    fun searchProducts(term: String): Flow<List<Product>>
}