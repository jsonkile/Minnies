package com.demo.minnies.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.auth.domain.GetCachedUserUseCaseImpl
import com.demo.minnies.auth.domain.LogoutUserUseCase
import com.demo.minnies.notifications.domain.GetUserPushNotificationsPreferenceUseCase
import com.demo.minnies.notifications.domain.SetUserPushNotificationsPreferenceUseCase
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCaseImpl
import com.demo.minnies.shared.domain.SetUserCurrencyPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreScreenViewModel @Inject constructor(
    private val logoutUserUseCase: LogoutUserUseCase,
    private val getCachedUserUseCaseImpl: GetCachedUserUseCaseImpl,
    private val setUserPushNotificationsPreferenceUseCase: SetUserPushNotificationsPreferenceUseCase,
    private val getUserPushNotificationsPreferenceUseCase: GetUserPushNotificationsPreferenceUseCase,
    private val getUserCurrencyPreferenceUseCaseImpl: GetUserCurrencyPreferenceUseCaseImpl,
    private val setUserCurrencyPreferenceUseCase: SetUserCurrencyPreferenceUseCase
) :
    ViewModel() {

    val isLoggedIn =
        getCachedUserUseCaseImpl().map { it != null }

    val pushNotificationsPreference = getUserPushNotificationsPreferenceUseCase()

    val currencyPreference = getUserCurrencyPreferenceUseCaseImpl()

    fun logout() {
        viewModelScope.launch {
            logoutUserUseCase()
        }
    }

    fun updatePushNotificationsPreference(preference: Boolean) {
        viewModelScope.launch {
            setUserPushNotificationsPreferenceUseCase(preference)
        }
    }

    fun updateCurrencyPreference(preference: String) {
        viewModelScope.launch {
            setUserCurrencyPreferenceUseCase(preference)
        }
    }
}