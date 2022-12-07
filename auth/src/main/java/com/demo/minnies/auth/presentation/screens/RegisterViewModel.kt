package com.demo.minnies.auth.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.auth.domain.RegisterUserUseCase
import com.demo.minnies.shared.utils.TIME_OUT_MESSAGE
import com.demo.minnies.shared.utils.encryption.Encryptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val encryptor: Encryptor
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Default)
    val uiState = _uiState.asStateFlow()


    fun register(emailAddress: String, password: String, fullName: String, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withTimeout(20_000) {
                    _uiState.value = UiState.Loading
                    delay(2000) // simulate network request
                    registerUserUseCase(
                        email = emailAddress,
                        password = encryptor.encrypt(password),
                        fullName = fullName,
                        phoneNumber = phoneNumber
                    )
                    _uiState.value = UiState.Default
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
    }
}