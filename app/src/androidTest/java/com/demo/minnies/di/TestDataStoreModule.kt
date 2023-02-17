package com.demo.minnies.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.demo.minnies.auth.di.DataStoreModule
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.UserSerializer
import com.demo.minnies.shared.utils.TEST_PREFERENCE_DATASTORE_FILE_NAME
import com.demo.minnies.shared.utils.TEST_PROTO_DATASTORE_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Singleton

//@OptIn(ExperimentalCoroutinesApi::class)
//@Module
//@TestInstallIn(components = [SingletonComponent::class], replaces = [DataStoreModule::class, com.demo.minnies.shared.di.DataStoreModule::class])
//object TestDataStoreModule {
//    @Singleton
//    @Provides
//    fun provideProtoDataStore(@ApplicationContext context: Context): DataStore<PartialUser?> {
//        return DataStoreFactory.create(
//            serializer = UserSerializer,
//            produceFile = { context.dataStoreFile(TEST_PROTO_DATASTORE_FILE_NAME) },
//            corruptionHandler = null,
//            migrations = emptyList(),
//            scope = TestScope(UnconfinedTestDispatcher() + Job())
//        )
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Singleton
//    @Provides
//    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
//        return PreferenceDataStoreFactory.create(
//            scope = TestScope(UnconfinedTestDispatcher() + Job()),
//            produceFile = { context.preferencesDataStoreFile(TEST_PREFERENCE_DATASTORE_FILE_NAME) }
//        )
//    }
//}