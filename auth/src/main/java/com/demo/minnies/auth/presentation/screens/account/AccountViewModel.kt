package com.demo.minnies.auth.presentation.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.auth.domain.GetUserUseCase
import com.demo.minnies.auth.domain.UpdateCachedUserUseCase
import com.demo.minnies.auth.domain.UpdateUserShippingAddressUseCase
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.database.room.models.ShippingAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getCachedUserUseCase: GetCachedUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserShippingAddressUseCase: UpdateUserShippingAddressUseCase,
    private val updateCachedUserUseCase: UpdateCachedUserUseCase
) : ViewModel() {

    private val _snackBarMessage = Channel<String>()
    val snackBarMessage = _snackBarMessage.receiveAsFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun fetchUser() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val userId = checkNotNull(getCachedUserUseCase().first()?.id)
                val user = getUserUseCase(userId).first()
                _uiState.update { it.copy(user = user) }
                user?.let { updateCachedUserUseCase(user) }
            } catch (e: Exception) {
                _snackBarMessage.send(e.message.orEmpty())
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateShippingAddress(newAddress: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val userId = checkNotNull(getCachedUserUseCase().first()?.id)
                val shippingAddress = ShippingAddress(id = userId, shippingAddress = newAddress)
                delay(1000) //simulate network call
                updateUserShippingAddressUseCase(shippingAddress)
                fetchUser()
            } catch (e: Exception) {
                _snackBarMessage.send(e.message.orEmpty())
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    data class UiState(val isLoading: Boolean = false, val user: PartialUser? = null)
}