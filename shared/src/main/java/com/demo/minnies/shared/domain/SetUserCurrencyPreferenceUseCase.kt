package com.demo.minnies.shared.domain

import com.demo.minnies.shared.data.repos.UserPreferencesRepository
import javax.inject.Inject

class SetUserCurrencyPreferenceUseCase @Inject constructor(private val repo: UserPreferencesRepository) {
    suspend operator fun invoke(preference: String) = repo.setCurrencyPreference(preference)
}