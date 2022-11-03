package com.demo.minnies.di

import android.content.Context
import androidx.room.Room
import com.demo.minnies.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.APP_DB_NAME).build()

    @Provides
    fun provideShopDao(db: AppDatabase) = db.shopDao()
}