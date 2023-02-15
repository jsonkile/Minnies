package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.auth.data.repos.UserRepo
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.shared.utils.BAD_LOGIN_MESSAGE
import com.demo.minnies.shared.utils.encryption.Encryptor
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class LoginUserUseCaseImplTest {
    @get:Rule
    val rule = MockKRule(this)

    @MockK(relaxed = true)
    lateinit var encryptor: Encryptor

    @MockK
    lateinit var cacheRepo: CacheRepo

    @MockK(relaxed = true)
    lateinit var userRepo: UserRepo

    @Test
    fun testLogin_BadEmail_ThrowsError() = runTest {
        coEvery { userRepo.getUser("email") } returns flow { emit(null) }

        val loginUserUseCase =
            LoginUserUseCaseImpl(userRepo = userRepo, cacheRepo = cacheRepo, encryptor = encryptor)

        Assert.assertThrows(
            BAD_LOGIN_MESSAGE, IllegalStateException::class.java
        ) { runBlocking { loginUserUseCase.invoke("email", "") } }
    }

    @Test
    fun testLogin_BadPassword_ThrowsError() = runTest {

        coEvery { userRepo.getUser("email") } returns flow {
            emit(PartialUser(fullName = "", emailAddress = "email", phoneNumber = ""))
        }

        coEvery { encryptor.decrypt("password") } returns "hook"

        coEvery { userRepo.peekPassword("email") } returns "password"

        val loginUserUseCase =
            LoginUserUseCaseImpl(userRepo = userRepo, cacheRepo = cacheRepo, encryptor = encryptor)

        Assert.assertThrows(
            BAD_LOGIN_MESSAGE, IllegalStateException::class.java
        ) { runBlocking { loginUserUseCase.invoke("email", "bad_password") } }

    }

    @Test
    fun testLogin_Successful() = runTest {
        var running = true

        val mockUser = PartialUser(fullName = "", emailAddress = "email", phoneNumber = "")

        coEvery { userRepo.getUser("email") } returns flow {
            emit(mockUser)
        }

        coEvery { encryptor.decrypt("password") } returns "hook"

        coEvery { userRepo.peekPassword("email") } returns "password"

        coEvery { cacheRepo.storeLoggedInUser(mockUser) } answers { running = running.not() }

        val loginUserUseCase =
            LoginUserUseCaseImpl(userRepo = userRepo, cacheRepo = cacheRepo, encryptor = encryptor)

        loginUserUseCase.invoke("email", "password")

        Assert.assertFalse(running)
    }
}