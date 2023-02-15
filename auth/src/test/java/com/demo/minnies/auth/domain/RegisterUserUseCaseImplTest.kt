package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.auth.data.repos.UserRepo
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.User
import com.demo.minnies.shared.utils.EMAIL_TAKEN_MESSAGE
import com.demo.minnies.shared.utils.GENERIC_ERROR_MESSAGE
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

internal class RegisterUserUseCaseImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var cacheRepo: CacheRepo

    @MockK(relaxed = true)
    lateinit var userRepo: UserRepo

    @Test
    fun testRegister_EmailTaken_ThrowsError() = runTest {

        coEvery { userRepo.getUser("email@test.com") } returns flow {
            emit(PartialUser(fullName = "", emailAddress = "email@test.com", phoneNumber = ""))
        }
        val registerUserUseCase = RegisterUserUseCaseImpl(userRepo, cacheRepo)
        Assert.assertThrows(EMAIL_TAKEN_MESSAGE, IllegalStateException::class.java) {
            runBlocking {
                registerUserUseCase(
                    "email@test.com", "", "John Doe", "899934444"
                )
            }
        }

    }

    @Test
    fun testRegister_GetUserFail_ThrowsError() = runTest {
        coEvery { userRepo.getUser("email@test.com") } returns flow { emit(null) }
        coEvery { userRepo.addUser(any()) } returns 0L
        coEvery { userRepo.getUser(0L) } returns flow { emit(null) }

        val registerUserUseCase = RegisterUserUseCaseImpl(userRepo, cacheRepo)

        Assert.assertThrows(GENERIC_ERROR_MESSAGE, IllegalStateException::class.java) {
            runBlocking {
                registerUserUseCase(
                    "email@test.com", "", "John Doe", "899934444"
                )
            }
        }

    }

    @Test
    fun testRegister_Successful() = runTest {
        var running = true
        val mockUser = PartialUser(emailAddress = "email@test.com", fullName = "", phoneNumber = "")
        coEvery { userRepo.getUser("email@test.com") } returns flow { emit(null) }
        coEvery { userRepo.addUser(any()) } returns 0L
        coEvery { userRepo.getUser(0L) } returns flow {
            emit(mockUser)
        }
        coEvery { cacheRepo.storeLoggedInUser(mockUser) } answers { running = !running }

        val registerUserUseCase = RegisterUserUseCaseImpl(userRepo, cacheRepo)
        registerUserUseCase(
            "email@test.com", "", "John Doe", "899934444"
        )

        Assert.assertFalse(running)
    }
}