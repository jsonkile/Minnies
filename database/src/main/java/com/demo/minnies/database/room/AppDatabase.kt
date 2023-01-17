package com.demo.minnies.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.minnies.database.room.converters.CategoryConverter
import com.demo.minnies.database.room.converters.IntListConverter
import com.demo.minnies.database.room.daos.CartDao
import com.demo.minnies.database.room.daos.ProductsDao
import com.demo.minnies.database.room.daos.UsersDao
import com.demo.minnies.database.room.models.CartItem
import com.demo.minnies.database.room.models.CartItemDetail
import com.demo.minnies.database.room.models.Product
import com.demo.minnies.database.room.models.User

@Database(
    version = 9,
    entities = [Product::class, User::class, CartItem::class],
    views = [CartItemDetail::class],
    exportSchema = false
)
@TypeConverters(value = [CategoryConverter::class, IntListConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductsDao

    abstract fun usersDao(): UsersDao

    abstract fun cartDao(): CartDao

    companion object {
        const val APP_DB_NAME = "minnies_db"
    }
}