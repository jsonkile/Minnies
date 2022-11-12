package com.demo.minnies.auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.demo.minnies.auth.data.models.PartialUser
import com.demo.minnies.auth.data.models.User
import com.demo.minnies.auth.utils.UserSerializer
import com.demo.minnies.shared.utils.PROTO_DATASTORE_FILE_NAME
import com.demo.minnies.shared.utils.TEST_PROTO_DATASTORE_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DataStoreModule::class])
object TestDataStoreModule {

    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext context: Context): DataStore<PartialUser?> {
        return DataStoreFactory.create(
            serializer = UserSerializer,
            produceFile = { context.dataStoreFile(TEST_PROTO_DATASTORE_FILE_NAME) },
            corruptionHandler = null,
            migrations = emptyList(),
            scope = TestScope(UnconfinedTestDispatcher() + Job())
        )
    }
}