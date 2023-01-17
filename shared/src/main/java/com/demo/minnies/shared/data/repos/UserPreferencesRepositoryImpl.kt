package com.demo.minnies.shared.data.repos

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.USER_PREFERENCE_CURRENCY_KEY
import com.demo.minnies.shared.utils.USER_PREFERENCE_NOTIFICATIONS_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    UserPreferencesRepository {
    override fun getNotificationsPreference(): Flow<Boolean> {
        return dataStore.data.map {
            it[booleanPreferencesKey(USER_PREFERENCE_NOTIFICATIONS_KEY)] ?: true
        }
    }

    override suspend fun setNotificationsPreference(preference: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(USER_PREFERENCE_NOTIFICATIONS_KEY)] = preference
        }
    }

    override fun getCurrencyPreference(): Flow<String> {
        return dataStore.data.map {
            it[stringPreferencesKey(USER_PREFERENCE_CURRENCY_KEY)] ?: Currency.USD.name
        }
    }

    override suspend fun setCurrencyPreference(preference: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(USER_PREFERENCE_CURRENCY_KEY)] = preference
        }
    }
}