package com.demo.minnies.notifications.domain

import com.demo.minnies.shared.data.repos.UserPreferencesRepository
import javax.inject.Inject

class SetUserPushNotificationsPreferenceUseCase @Inject constructor(private val repo: UserPreferencesRepository) {
    suspend operator fun invoke(preference: Boolean) = repo.setNotificationsPreference(preference)
}