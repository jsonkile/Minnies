package com.demo.minnies.di

import android.content.Context
import androidx.room.Room
import com.demo.minnies.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DBModule::class])
object TestDBModule {

    @Singleton
    @Provides
    fun providesTestRoomDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

    @Singleton
    @Provides
    fun provideShopDao(db: AppDatabase) = db.shopDao()
}