package com.demo.minnies.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.minnies.database.room.converters.CategoryConverter
import com.demo.minnies.database.room.converters.IntListConverter
import com.demo.minnies.database.room.daos.ShopDao
import com.demo.minnies.database.room.daos.UsersDao
import com.demo.minnies.database.room.models.ShopItem
import com.demo.minnies.database.room.models.User

@Database(version = 3, entities = [ShopItem::class, User::class], exportSchema = false)
@TypeConverters(value = [CategoryConverter::class, IntListConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao

    abstract fun usersDao(): UsersDao

    companion object {
        const val APP_DB_NAME = "minnies_db"
    }
}