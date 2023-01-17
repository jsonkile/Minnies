package com.demo.minnies.auth.presentation.screens

import app.cash.turbine.test
import com.demo.minnies.auth.domain.RegisterUserUseCase
import com.demo.minnies.shared.utils.encryption.Encryptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class RegisterViewModelTest {

    private val scheduler: TestCoroutineScheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val scope = TestScope(dispatcher)

    @Test
    fun `test that ui state moves from default to loading to error when there is an exception during register`() =
        runTest {

            Dispatchers.setMain(dispatcher)

            try {
                val registerUserUseCase = object : RegisterUserUseCase {
                    override suspend fun invoke(
                        email: String,
                        password: String,
                        fullName: String,
                        phoneNumber: String
                    ) {
                        throw Exception()
                    }
                }

                val encryptor = object : Encryptor {
                    override suspend fun encrypt(data: String) = ""

                    override suspend fun decrypt(data: String) = ""
                }
                val registerViewModel = RegisterViewModel(registerUserUseCase, encryptor)

                registerViewModel.uiState.test(2000.seconds) {
                    registerViewModel.register("test", "test", "Test", "")
                    Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Default)
                    Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Loading)
                    Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Error)
                }
            } finally {
                Dispatchers.resetMain()
            }
        }

    @Test
    fun `test that ui state moves from default to loading to success when register passes`() =
        runTest {

            Dispatchers.setMain(dispatcher)

            try {
                val registerUserUseCase = object : RegisterUserUseCase {
                    override suspend fun invoke(
                        email: String,
                        password: String,
                        fullName: String,
                        phoneNumber: String
                    ) {

                    }
                }

                val encryptor = object : Encryptor {
                    override suspend fun encrypt(data: String) = ""

                    override suspend fun decrypt(data: String) = ""
                }
                val registerViewModel = RegisterViewModel(registerUserUseCase, encryptor)

                registerViewModel.uiState.test(2000.seconds) {
                    registerViewModel.register("test", "test", "test", "test")
                    Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Default)
                    Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Loading)
                    Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Success)
                }
            } finally {
                Dispatchers.resetMain()
            }
        }
}