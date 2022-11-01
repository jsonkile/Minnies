package com.demo.minnies.shop.repos

import android.content.Context
import androidx.room.Room
import com.demo.minnies.database.di.DBModule
import com.demo.minnies.database.room.AppDatabase
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.ShopItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@UninstallModules(DBModule::class)
@HiltAndroidTest
class ShopRepoImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repo: ShopRepo

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestDBModule {

        @Provides
        fun providesTestRoomDatabase(@ApplicationContext context: Context) =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        @Provides
        fun provideShopDao(db: AppDatabase) = db.shopDao()
    }

    @Test
    fun addItemsAndGetAllItems_AddsAndReturnsItems_WhenAvailable() {
        runTest {
            repo.addItem(
                ShopItem(
                    name = "Test Item", category = Category.Shorts, image = "",
                    sizes = listOf(0), description = ""
                )
            )

            repo.addItem(
                ShopItem(
                    name = "Test Item", category = Category.Shorts, image = "",
                    sizes = listOf(0), description = ""
                )
            )

            Assert.assertEquals(2, repo.getAllItems().first().size)
        }
    }

    @Test
    fun addItemsAndGetItemsByCategory_AddsAndReturnsItems_WhenAvailable() {
        runTest {
            repo.addItem(
                ShopItem(
                    name = "Test Item", category = Category.Shorts, image = "",
                    sizes = listOf(0), description = ""
                )
            )

            repo.addItem(
                ShopItem(
                    name = "Test Item", category = Category.Top, image = "",
                    sizes = listOf(0), description = ""
                )
            )

            Assert.assertEquals(1, repo.getItemsByCategory(Category.Top).first().size)
        }
    }

}