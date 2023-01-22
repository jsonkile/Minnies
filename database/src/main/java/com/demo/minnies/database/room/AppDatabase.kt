package com.demo.minnies.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.minnies.database.models.*
import com.demo.minnies.database.room.converters.*
import com.demo.minnies.database.room.daos.CartDao
import com.demo.minnies.database.room.daos.ProductsDao
import com.demo.minnies.database.room.daos.UsersDao
import com.demo.minnies.database.room.daos.OrdersDao

@Database(
    version = 10,
    entities = [Product::class, User::class, CartItem::class, Order::class],
    views = [CartItemDetail::class],
    exportSchema = false
)
@TypeConverters(value = [ProductCategoryConverter::class, IntListConverter::class, CartStatusConverter::class, LongListConverter::class, OrderStatusConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductsDao

    abstract fun usersDao(): UsersDao

    abstract fun cartDao(): CartDao

    abstract fun ordersDao(): OrdersDao

    companion object {
        const val APP_DB_NAME = "minnies_db"
    }
}