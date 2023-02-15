package com.demo.minnies.auth.presentation.screens.account

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.auth.domain.GetUserUseCase
import com.demo.minnies.auth.domain.UpdateCachedUserUseCase
import com.demo.minnies.auth.domain.UpdateUserShippingAddressUseCase
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.ShippingAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Test

internal class AccountViewModelTest {

    val scheduler = TestCoroutineScheduler()
    val dispatcher = UnconfinedTestDispatcher(scheduler)
    val scope = TestScope(dispatcher)

    @Test
    fun testFetchUser() = runTest {
        try {
            Dispatchers.setMain(dispatcher)

            val getCachedUserUseCase = object : GetCachedUserUseCase {
                override fun invoke(): Flow<PartialUser?> = flow {
                    emit(
                        PartialUser(
                            id = 1, fullName = "name", emailAddress = "email", phoneNumber = "phone"
                        )
                    )
                }
            }
            val getUserUseCase = object : GetUserUseCase {
                override fun invoke(id: Long): Flow<PartialUser?> = flow {
                    emit(
                        PartialUser(
                            id = 1, fullName = "name", emailAddress = "email", phoneNumber = "phone"
                        )
                    )
                }
            }
            val updateUserShippingAddressUseCase = object : UpdateUserShippingAddressUseCase {
                override suspend fun invoke(shippingAddress: ShippingAddress): PartialUser? {
                    throw RuntimeException("mega")
                }
            }
            val updateCachedUserUseCase = object : UpdateCachedUserUseCase {
                override suspend fun invoke(user: PartialUser) {

                }
            }

            val viewModel = AccountViewModel(
                getCachedUserUseCase,
                getUserUseCase,
                updateUserShippingAddressUseCase,
                updateCachedUserUseCase,
                dispatcher
            )

            Assert.assertNull(viewModel.uiState.value.user)
            viewModel.fetchUser()
            Assert.assertNotNull(viewModel.uiState.value.user)
            Assert.assertEquals("email", viewModel.uiState.value.user?.emailAddress)
            Assert.assertFalse(viewModel.uiState.value.isLoading)

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun testUpdateShippingAddress() = runTest {
        var step = 0

        val getCachedUserUseCase = object : GetCachedUserUseCase {
            override fun invoke(): Flow<PartialUser?> = flow {
                emit(
                    PartialUser(
                        id = 1, fullName = "name", emailAddress = "email", phoneNumber = "phone"
                    )
                )
            }
        }

        val getUserUseCase = object : GetUserUseCase {
            override fun invoke(id: Long): Flow<PartialUser?> = flow {
                emit(
                    PartialUser(
                        id = 1, fullName = "name", emailAddress = "email", phoneNumber = "phone"
                    )
                )
            }
        }

        val updateUserShippingAddressUseCase = object : UpdateUserShippingAddressUseCase {
            override suspend fun invoke(shippingAddress: ShippingAddress): PartialUser? {
                step++
                return null
            }
        }

        val updateCachedUserUseCase = object : UpdateCachedUserUseCase {
            override suspend fun invoke(user: PartialUser) {}
        }

        val viewModel = AccountViewModel(
            getCachedUserUseCase,
            getUserUseCase,
            updateUserShippingAddressUseCase,
            updateCachedUserUseCase,
            dispatcher
        )

        viewModel.updateShippingAddress("goal")

        Assert.assertEquals(1, step)
    }

    @Test
    fun testSnackBarMessage() = runTest {
        val getCachedUserUseCase = object : GetCachedUserUseCase {
            override fun invoke(): Flow<PartialUser?> = flow {
                emit(
                    PartialUser(
                        id = 1, fullName = "name", emailAddress = "email", phoneNumber = "phone"
                    )
                )
            }
        }

        val getUserUseCase = object : GetUserUseCase {
            override fun invoke(id: Long): Flow<PartialUser?> = flow {
                emit(
                    PartialUser(
                        id = 1, fullName = "name", emailAddress = "email", phoneNumber = "phone"
                    )
                )
            }
        }

        val updateUserShippingAddressUseCase = object : UpdateUserShippingAddressUseCase {
            override suspend fun invoke(shippingAddress: ShippingAddress): PartialUser? {
                throw RuntimeException("mega")
            }
        }

        val updateCachedUserUseCase = object : UpdateCachedUserUseCase {
            override suspend fun invoke(user: PartialUser) {

            }
        }

        val viewModel = AccountViewModel(
            getCachedUserUseCase,
            getUserUseCase,
            updateUserShippingAddressUseCase,
            updateCachedUserUseCase,
            dispatcher
        )

        val values = mutableListOf<String>()
        val collectJob = launch(UnconfinedTestDispatcher(scheduler)) {
            viewModel.snackBarMessage.toList(values)
        }

        viewModel.updateShippingAddress("")

        Assert.assertEquals(1, values.size)

        collectJob.cancel()
    }
}