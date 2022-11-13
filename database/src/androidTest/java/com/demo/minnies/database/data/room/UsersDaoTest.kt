package com.demo.minnies.database.data.room

import com.demo.minnies.database.room.AppDatabase
import com.demo.minnies.database.room.daos.UsersDao
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.database.room.models.User
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class UsersDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var dao: UsersDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun finish() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun getUser_ReturnsNull_WhenNoMatch() {
        runTest {
            Assert.assertNull(dao.getUserByEmail("email").first())
        }
    }

    @Test
    fun getUserByEmailAndPassword_WithExistingEmailAndPassword_ReturnsCorrectMatch() {
        runTest {
            dao.addUser(User("a", "b", "c", "d"))
            dao.addUser(User("e", "f", "g", "h"))
            Assert.assertNotNull(dao.getUserByEmailAndPassword(emailAddress = "f", password = "h").first())
        }
    }

    @Test
    fun getUserByEmailAndPassword_WithNonExistingEmailAndPassword_ReturnsNoMatch() {
        runTest {
            dao.addUser(User("a", "b", "c", "d"))
            dao.addUser(User("e", "f", "g", "h"))
            Assert.assertNull(dao.getUserByEmailAndPassword(emailAddress = "8", password = "h").first())
        }
    }

    @Test
    fun addUser_SuccessfullyInsertsUser() {
        runTest {
            dao.addUser(User("John", "kalu", "Q", "hea¬"))
            Assert.assertNotNull(dao.getUserByEmail("kalu").first())
            Assert.assertEquals("Q", dao.getUserByEmail("kalu").first()?.phoneNumber)
            Assert.assertEquals("John", dao.getUserByEmail("kalu").first()?.fullName)
        }
    }

    @Test
    fun updateUser_SuccessfullyUpdatesUserData() {
        runTest {
            dao.addUser(User("John", "kalu", "98", "hea¬"))
            Assert.assertEquals("John", dao.getUserByEmail("kalu").first()?.fullName)
            dao.updateUser(PartialUser("Mike", "kalu", "98"))
            Assert.assertEquals("Mike", dao.getUserByEmail("kalu").first()?.fullName)
        }
    }

    @Test
    fun deleteUser_SuccessfullyDeletesUserData() {
        runTest {
            dao.addUser(User("John", "kalu", "98", "hea¬"))
            Assert.assertNotNull(dao.getUserByEmail("kalu").first())
            dao.deleteUser(PartialUser("John", "kalu", "98"))
            Assert.assertNull(dao.getUserByEmail("kalu").first())
        }
    }
}