package com.demo.minnies.auth.presentation.screens

import app.cash.turbine.test
import com.demo.minnies.auth.data.repos.AuthRepo
import com.demo.minnies.auth.data.repos.UserRepo
import com.demo.minnies.auth.domain.LoginUserUseCase
import com.demo.minnies.shared.utils.customByteArray
import com.demo.minnies.shared.utils.encryption.Encryptor
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
internal class LoginViewModelTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val scope = TestScope(dispatcher)

    @Inject
    lateinit var loginUserUseCase: LoginUserUseCase

    @Inject
    lateinit var encryptor: Encryptor

    @Inject
    lateinit var authRepo: AuthRepo

    @Inject
    lateinit var userRepo: UserRepo

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        scope.cancel()

        loginViewModel = LoginViewModel(loginUserUseCase, encryptor)
    }

    @Test
    fun uiState_DefaultsAsDefault() {
        Assert.assertTrue(loginViewModel.uiState.value is LoginViewModel.UiState.Default)
    }

    @Test
    fun uiState_ChangesToLoading_WhenLoginStarts() = runTest(dispatcher) {
        loginViewModel.uiState.test {

            loginViewModel.login("", "")
            Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Default)
            Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Loading)
            cancel()
        }
    }

    @Test
    fun uiState_ChangesToError_whenLoginFails() = runTest(dispatcher) {
        loginViewModel.uiState.test(timeout = 5.seconds) {

            loginViewModel.login("", "")

            Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Default)
            Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Loading)
            Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Error)
        }
    }

    @Test
    fun uiState_ChangesToDefault_whenLoginSuccess() = runTest(dispatcher) {
        loginViewModel.uiState.test(timeout = 5.seconds) {

            val fakeEmail = "tester${System.currentTimeMillis()}"

            val newUser = authRepo.register(
                emailAddress = fakeEmail,
                password = encryptor.encrypt("password"),
                fullName = "f", phoneNumber = "3"
            ).first()

            Assert.assertNotNull(userRepo.getUser(fakeEmail).first())

            Assert.assertEquals("f", newUser?.fullName.orEmpty())
            Assert.assertEquals("3", newUser?.phoneNumber.orEmpty())
            Assert.assertEquals(fakeEmail, newUser?.emailAddress.orEmpty())

            loginViewModel.login(fakeEmail, "password")

            Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Default)
            Assert.assertTrue(awaitItem() is LoginViewModel.UiState.Loading)
            Assert.assertEquals(
                "Please enter a valid email address.",
                (awaitItem() as LoginViewModel.UiState.Error).throwable.message
            )
            ensureAllEventsConsumed()
        }
    }
}