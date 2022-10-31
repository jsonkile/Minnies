package com.demo.minnies.database.room

import com.demo.minnies.database.room.daos.ShopDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class ShopDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var dao: ShopDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun getAll_returnsZeroItems_WhenTableIsEmpty() {
        runTest {
            Assert.assertEquals(0, dao.getAll().first().size)
        }
    }
}