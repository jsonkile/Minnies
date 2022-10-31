package com.demo.minnies.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.minnies.database.room.converters.CategoryConverter
import com.demo.minnies.database.room.converters.IntListConverter
import com.demo.minnies.database.room.daos.ShopDao
import com.demo.minnies.database.room.models.ShopItem

@Database(version = 1, entities = [ShopItem::class], exportSchema = false)
@TypeConverters(value = [CategoryConverter::class, IntListConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao

    companion object {
        const val APP_DB_NAME = "minnies_db"
    }
}