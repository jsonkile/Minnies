package com.demo.minnies.presentation

import androidx.lifecycle.ViewModel
import com.demo.minnies.auth.domain.GetCachedUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(getCachedUserUseCase: GetCachedUserUseCase) :
    ViewModel() {

        val loggedInUser = getCachedUserUseCase()

}