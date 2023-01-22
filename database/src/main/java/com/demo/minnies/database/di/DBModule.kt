package com.demo.minnies.database.di

import android.content.Context
import androidx.room.Room
import com.demo.minnies.database.room.AppDatabase
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
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.APP_DB_NAME)
            .fallbackToDestructiveMigration().build()

    @Provides
    fun provideProductsDao(db: AppDatabase) = db.productsDao()

    @Provides
    fun providesUsersDao(db: AppDatabase) = db.usersDao()

    @Provides
    fun providesCartDao(db: AppDatabase) = db.cartDao()

    @Provides
    fun providesOrdersDao(db: AppDatabase) = db.ordersDao()
}