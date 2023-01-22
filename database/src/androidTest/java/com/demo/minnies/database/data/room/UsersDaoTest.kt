package com.demo.minnies.database.data.room

import com.demo.minnies.database.room.AppDatabase
import com.demo.minnies.database.room.daos.UsersDao
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.User
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
            dao.addUser(User(fullName = "a", emailAddress = "b", phoneNumber = "c", password = "d", shippingAddress = "q"))
            dao.addUser(User(fullName = "e", emailAddress = "f", phoneNumber = "g", password = "h", shippingAddress = "o"))
            Assert.assertNotNull(
                dao.getUserByEmailAndPassword(emailAddress = "f", password = "h").first()
            )
            Assert.assertEquals("o", dao.getUserByEmail("f").first()?.shippingAddress)
        }
    }

    @Test
    fun getUserByEmailAndPassword_WithNonExistingEmailAndPassword_ReturnsNoMatch() {
        runTest {
            dao.addUser(User(fullName = "a", emailAddress = "b", phoneNumber = "c", password = "d"))
            dao.addUser(User(fullName = "e", emailAddress = "f", phoneNumber = "g", password = "h"))
            Assert.assertNull(
                dao.getUserByEmailAndPassword(emailAddress = "8", password = "h").first()
            )
        }
    }


    @Test
    fun getPasswordByEmail_WithExistingEmail_ReturnsMatch() {
        runTest {
            dao.addUser(
                User(
                    fullName = "a1",
                    emailAddress = "b1",
                    phoneNumber = "c1",
                    password = "d1"
                )
            )
            Assert.assertEquals(dao.getPasswordByEmail(emailAddress = "b1"), "d1")
        }
    }

    @Test
    fun getPasswordByEmail_WithNonExistingEmail_ReturnsNoMatch() {
        runTest {
            dao.addUser(
                User(
                    fullName = "a1",
                    emailAddress = "b1",
                    phoneNumber = "c1",
                    password = "d1"
                )
            )
            Assert.assertNull(dao.getPasswordByEmail(emailAddress = "2"))
        }
    }

    @Test
    fun addUser_SuccessfullyInsertsUser() {
        runTest {
            dao.addUser(
                User(
                    fullName = "John",
                    emailAddress = "kalu",
                    phoneNumber = "Q",
                    password = "hea¬"
                )
            )
            Assert.assertNotNull(dao.getUserByEmail("kalu").first())
            Assert.assertEquals("Q", dao.getUserByEmail("kalu").first()?.phoneNumber)
            Assert.assertEquals("John", dao.getUserByEmail("kalu").first()?.fullName)
        }
    }

    @Test
    fun updateUser_SuccessfullyUpdatesUserData() {
        runTest {
            dao.addUser(
                User(
                    fullName = "John",
                    emailAddress = "kalu",
                    phoneNumber = "98",
                    password = "hea¬",
                    shippingAddress = "ship"
                )
            )
            Assert.assertEquals("John", dao.getUserByEmail("kalu").first()?.fullName)
            dao.updateUser(
                PartialUser(
                    fullName = "Mike",
                    emailAddress = "kalu",
                    phoneNumber = "98",
                    shippingAddress = "ship2"
                )
            )
            Assert.assertEquals("Mike", dao.getUserByEmail("kalu").first()?.fullName)
            Assert.assertEquals("ship2", dao.getUserByEmail("kalu").first()?.shippingAddress)
        }
    }

    @Test
    fun deleteUser_SuccessfullyDeletesUserData() {
        runTest {
            dao.addUser(
                User(
                    fullName = "John",
                    emailAddress = "kalu",
                    phoneNumber = "98",
                    password = "hea¬"
                )
            )
            Assert.assertNotNull(dao.getUserByEmail("kalu").first())
            dao.deleteUser(
                PartialUser(
                    fullName = "John",
                    emailAddress = "kalu",
                    phoneNumber = "98"
                )
            )
            Assert.assertNull(dao.getUserByEmail("kalu").first())
        }
    }
}