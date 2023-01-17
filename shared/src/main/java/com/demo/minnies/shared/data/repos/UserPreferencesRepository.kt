package com.demo.minnies.shared.data.repos

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getNotificationsPreference(): Flow<Boolean>

    suspend fun setNotificationsPreference(preference: Boolean)

    fun getCurrencyPreference(): Flow<String>

    suspend fun setCurrencyPreference(preference: String)
}