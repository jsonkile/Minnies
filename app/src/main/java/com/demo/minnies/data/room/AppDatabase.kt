package com.demo.minnies.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.minnies.auth.data.daos.UsersDao
import com.demo.minnies.auth.data.models.User
import com.demo.minnies.data.room.converters.CategoryConverter
import com.demo.minnies.data.room.converters.IntListConverter
import com.demo.minnies.shop.data.daos.ShopDao
import com.demo.minnies.shop.data.models.ShopItem

@Database(version = 2, entities = [ShopItem::class, User::class], exportSchema = false)
@TypeConverters(value = [CategoryConverter::class, IntListConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao

    abstract fun usersDao(): UsersDao

    companion object {
        const val APP_DB_NAME = "minnies_db"
    }
}