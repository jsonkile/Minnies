package com.demo.minnies.database

import android.content.Context
import androidx.room.Room
import com.demo.minnies.database.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DBModule::class])
object TestDBModule {

    @Provides
    fun providesTestRoomDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

    @Provides
    fun provideShopDao(db: AppDatabase) = db.shopDao()
}