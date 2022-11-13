package com.demo.minnies.auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.database.room.models.UserSerializer
import com.demo.minnies.shared.utils.PROTO_DATASTORE_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext context: Context): DataStore<PartialUser?> {
        return DataStoreFactory.create(
            serializer = UserSerializer,
            produceFile = { context.dataStoreFile(PROTO_DATASTORE_FILE_NAME) },
            corruptionHandler = null,
            migrations = emptyList(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}