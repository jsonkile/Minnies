package com.demo.minnies.shared.data.repos

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.demo.minnies.shared.utils.PREFERENCE_INITIALIZATION_VECTOR_KEY
import com.demo.minnies.shared.utils.PREFERENCE_SECRET_KEY_KEY
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class KeysRepositoryDataStoreImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    KeysRepository {
    override suspend fun storeEncryptionSecretKey(key: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(PREFERENCE_SECRET_KEY_KEY)] = key
        }
    }

    override suspend fun getEncryptionSecretKey(): String {
        return dataStore.data.first()[stringPreferencesKey(PREFERENCE_SECRET_KEY_KEY)].orEmpty()
    }

    override suspend fun storeEncryptionInitializationVectorKey(key: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(PREFERENCE_INITIALIZATION_VECTOR_KEY)] = key
        }
    }

    override suspend fun getEncryptionInitializationVectorKey(): String {
        return dataStore.data.first()[stringPreferencesKey(PREFERENCE_INITIALIZATION_VECTOR_KEY)].orEmpty()
    }
}