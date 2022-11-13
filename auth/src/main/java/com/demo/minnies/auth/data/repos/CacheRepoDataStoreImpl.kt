package com.demo.minnies.auth.data.repos

import androidx.datastore.core.DataStore
import com.demo.minnies.database.room.models.PartialUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheRepoDataStoreImpl @Inject constructor(private val dataStore: DataStore<PartialUser?>) :
    CacheRepo {

    override suspend fun storeLoggedInUser(user: PartialUser) {
        dataStore.updateData { user }
    }

    override fun getLoggedInUser(): Flow<PartialUser?> = dataStore.data

    override suspend fun clear() {
        dataStore.updateData { null }
    }
}