package com.demo.minnies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.auth.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    getCachedUserUseCaseImpl: GetCachedUserUseCaseImpl,
    private val getUserUseCase: GetUserUseCase,
    private val getCachedUserUseCase: GetCachedUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val updateCachedUserUseCase: UpdateCachedUserUseCase
) :
    ViewModel() {

    val loggedInUser = getCachedUserUseCaseImpl()

    init {
        vertCachedUser()
    }

    private fun vertCachedUser() {
        viewModelScope.launch {
            try {
                val cachedUser = getCachedUserUseCase().first()
                cachedUser?.let {
                    val user = getUserUseCase(cachedUser.id).first()
                    if (user == null) logoutUserUseCase() else updateCachedUserUseCase(user)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

}