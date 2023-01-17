package com.demo.minnies.auth.presentation.screens

import app.cash.turbine.test
import com.demo.minnies.auth.domain.LoginUserUseCase
import com.demo.minnies.shared.utils.encryption.Encryptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class LoginViewModelTest {

    private val scheduler: TestCoroutineScheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val scope = TestScope(dispatcher)

    @Test
    fun `test that ui state moves from default to loading to error when there is an exception during login`() =
        runTest {

            Dispatchers.setMain(dispatcher)

            try {
                val loginUserUseCase = object : LoginUserUseCase {
                    override suspend fun invoke(email: String, password: String) {
                        throw Exception()
                    }
                }

                val encryptor = object : Encryptor {
                    override suspend fun encrypt(data: String) = ""

                    override suspend fun decrypt(data: String) = ""
                }
                val loginViewModel = LoginViewModel(loginUserUseCase, encryptor)

                loginViewModel.uiState.test(2000.seconds) {
                    loginViewModel.login("test", "test")
                    Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Default)
                    Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Loading)
                    Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Error)
                }
            } finally {
                Dispatchers.resetMain()
            }
        }

    @Test
    fun `test that ui state moves from default to loading to success when login passes`() =
        runTest {

            Dispatchers.setMain(dispatcher)

            try {
                val loginUserUseCase = object : LoginUserUseCase {
                    override suspend fun invoke(email: String, password: String) {

                    }
                }

                val encryptor = object : Encryptor {
                    override suspend fun encrypt(data: String) = ""

                    override suspend fun decrypt(data: String) = ""
                }
                val loginViewModel = LoginViewModel(loginUserUseCase, encryptor)

                loginViewModel.uiState.test(2000.seconds) {
                    loginViewModel.login("test", "test")
                    Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Default)
                    Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Loading)
                    Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Success)
                }
            } finally {
                Dispatchers.resetMain()
            }
        }
}