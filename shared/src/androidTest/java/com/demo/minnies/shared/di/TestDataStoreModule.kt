package com.demo.minnies.shared.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.demo.minnies.shared.utils.PREFERENCE_DATASTORE_FILE_NAME
import com.demo.minnies.shared.utils.TEST_PREFERENCE_DATASTORE_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import javax.inject.Singleton

//@Module
//@TestInstallIn(components = [SingletonComponent::class], replaces = [DataStoreModule::class])
//object TestDataStoreModule {
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Provides
//    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
//        return PreferenceDataStoreFactory.create(
//            scope = TestScope(UnconfinedTestDispatcher() + Job()),
//            produceFile = { context.preferencesDataStoreFile(TEST_PREFERENCE_DATASTORE_FILE_NAME) }
//        )
//    }
//
//}