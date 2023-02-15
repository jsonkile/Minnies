package com.demo.minnies.auth.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.auth.domain.LoginUserUseCase
import com.demo.minnies.shared.utils.TIME_OUT_MESSAGE
import com.demo.minnies.shared.utils.encryption.Encryptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val encryptor: Encryptor
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Default)
    val uiState = _uiState.asStateFlow()

    fun login(emailAddress: String, password: String) {
        viewModelScope.launch {
            try {
                withTimeout(20_000) {
                    _uiState.value = UiState.Loading
                    delay(2000) // simulate network request
                    loginUserUseCase(emailAddress, encryptor.encrypt(password))

                    _uiState.value = UiState.Success
                }
            } catch (e: Exception) {
                _uiState.value = when (e) {
                    is CancellationException -> UiState.Error(Throwable(TIME_OUT_MESSAGE))
                    else -> UiState.Error(e)
                }
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        class Error(val throwable: Throwable) : UiState()
        object Default : UiState()
        object Success : UiState()
    }
}