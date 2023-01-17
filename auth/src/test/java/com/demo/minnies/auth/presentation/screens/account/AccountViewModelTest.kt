package com.demo.minnies.auth.presentation.screens.account

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.auth.domain.GetUserUseCase
import com.demo.minnies.auth.domain.UpdateUserShippingAddressUseCase
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.database.room.models.ShippingAddress
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
                override fun invoke(shippingAddress: ShippingAddress) {}
            }

            val viewModel = AccountViewModel(
                getCachedUserUseCase,
                getUserUseCase,
                updateUserShippingAddressUseCase
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
                override fun invoke(shippingAddress: ShippingAddress) {}
            }

            val viewModel = AccountViewModel(
                getCachedUserUseCase,
                getUserUseCase,
                updateUserShippingAddressUseCase
            )

            Assert.assertNull(viewModel.uiState.value.user)
            viewModel.updateShippingAddress("")
            Assert.assertFalse(viewModel.uiState.value.isLoading)

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun testSnackBarMessage() = runTest {
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
                override fun invoke(shippingAddress: ShippingAddress) {
                    throw RuntimeException("mega")
                }
            }

            val viewModel = AccountViewModel(
                getCachedUserUseCase,
                getUserUseCase,
                updateUserShippingAddressUseCase
            )

            val values = mutableListOf<String>()
            val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.snackBarMessage.toList(values)
            }

            viewModel.updateShippingAddress("")
            Assert.assertEquals(values.size, 1)

            collectJob.cancel()
        } finally {
            Dispatchers.resetMain()
        }
    }
}