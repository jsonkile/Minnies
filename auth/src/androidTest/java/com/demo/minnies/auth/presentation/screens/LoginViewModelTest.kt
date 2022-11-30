package com.demo.minnies.auth.presentation.screens

import com.demo.minnies.auth.data.repos.AuthRepo
import com.demo.minnies.auth.domain.LoginUserUseCase
import com.demo.minnies.shared.utils.encryption.Encryptor
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
internal class LoginViewModelTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Inject
    lateinit var loginUserUseCase: LoginUserUseCase

    @Inject
    lateinit var encryptor: Encryptor

    @Inject
    lateinit var authRepo: AuthRepo

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
    fun uiState_ChangesToLoading_WhenLoginStarts() {
        scope.launch {
            loginViewModel.login("", "")
            val states = loginViewModel.uiState.take(2).toList()
            Assert.assertTrue(states.first() is LoginViewModel.UiState.Default)
            Assert.assertTrue(states[1] is LoginViewModel.UiState.Loading)
        }
    }

    @Test
    fun login_ChangesToError_whenLoginFails() {
        scope.launch {
            loginViewModel.login("", "")
            val states = loginViewModel.uiState.take(3).toList()
            Assert.assertTrue(states.first() is LoginViewModel.UiState.Default)
            Assert.assertTrue(states[1] is LoginViewModel.UiState.Loading)
            Assert.assertTrue(states[2] is LoginViewModel.UiState.Error)
        }
    }

    @Test
    fun login_ChangesToDefault_whenLoginSuccess() {
        scope.launch {
            authRepo.register(
                emailAddress = "baba",
                phoneNumber = "080",
                fullName = "baba",
                password = encryptor.encrypt("pass")
            ).first()

            loginViewModel.login("baba", encryptor.encrypt("pass"))
            val states = loginViewModel.uiState.take(3).toList()
            Assert.assertTrue(states.first() is LoginViewModel.UiState.Default)
            Assert.assertTrue(states[1] is LoginViewModel.UiState.Loading)
            Assert.assertTrue(states[2] is LoginViewModel.UiState.Default)
        }

    }
}