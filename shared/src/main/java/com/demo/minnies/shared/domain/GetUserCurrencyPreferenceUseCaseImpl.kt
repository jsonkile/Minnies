package com.demo.minnies.shared.domain

import com.demo.minnies.shared.data.repos.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface GetUserCurrencyPreferenceUseCase {
    operator fun invoke(): Flow<String>
}

class GetUserCurrencyPreferenceUseCaseImpl @Inject constructor(private val repo: UserPreferencesRepository) :
    GetUserCurrencyPreferenceUseCase {

    override operator fun invoke(): Flow<String> = repo.getCurrencyPreference()
}