package com.demo.minnies.auth.presentation.screens

import app.cash.turbine.test
import com.demo.minnies.auth.data.repos.AuthRepo
import com.demo.minnies.auth.domain.RegisterUserUseCase
import com.demo.minnies.shared.utils.encryption.Encryptor
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
internal class RegisterViewModelTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val scope = TestScope(dispatcher)

    @Inject
    lateinit var registerUserUseCase: RegisterUserUseCase

    @Inject
    lateinit var encryptor: Encryptor

    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setup() {
        hiltAndroidRule.inject()

        registerViewModel = RegisterViewModel(registerUserUseCase, encryptor)
    }

    @After
    fun cleanUp() {
        scope.cancel()
    }

    @Test
    fun uiState_DefaultsAsDefault() {
        Assert.assertTrue(registerViewModel.uiState.value is RegisterViewModel.UiState.Default)
    }

    @Test
    fun register_ChangesToError_whenRegisterFails() = runTest(dispatcher) {
        registerViewModel.uiState.test(timeout = 5.seconds) {
            registerViewModel.register("", "", "", "")

            Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Default)
            Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Loading)
            Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Error)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun register_ShowsCorrectError_whenRegisterFailsDueToEmailError() = runTest(dispatcher) {
        registerViewModel.uiState.test(timeout = 5.seconds) {

            registerViewModel.register("jason", "", "jayson kile", "099394343")

            Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Default)
            Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Loading)
            assertEquals(
                "Please enter a valid email address.",
                (awaitItem() as RegisterViewModel.UiState.Error).throwable.message
            )
            ensureAllEventsConsumed()

        }
    }

    @Test
    fun register_ShowsCorrectError_whenRegisterFailsDueToNameError() = runTest(dispatcher) {
        registerViewModel.uiState.test(timeout = 5.seconds) {

            registerViewModel.register("jason@gmail.com", "8jU!ds", "jayson", "099394343")

            Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Default)
            Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Loading)
            assertEquals(
                "Please enter your full name.",
                (awaitItem() as RegisterViewModel.UiState.Error).throwable.message
            )
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun uiState_ChangesToDefault_whenRegisterSuccess() = runTest(dispatcher) {
        registerViewModel.uiState.test(timeout = 5.seconds) {
            registerViewModel.register(
                "baba${System.currentTimeMillis()}@gmail.co",
                "pass",
                "jon mark",
                "09899998777"
            )

            Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Default)
            Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Loading)
            Assert.assertTrue(awaitItem() is RegisterViewModel.UiState.Default)
            ensureAllEventsConsumed()
        }
    }
}