package com.demo.minnies.shared.di

import com.demo.minnies.shared.data.repos.KeysPreferencesRepository
import com.demo.minnies.shared.data.repos.KeysPreferencesRepositoryDataStoreImpl
import com.demo.minnies.shared.utils.encryption.AESEncryptorImpl
import com.demo.minnies.shared.utils.encryption.Encryptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SharedModule {

    @Binds
    abstract fun providesEncryptor(aesEncryptorImpl: AESEncryptorImpl): Encryptor

    @Binds
    abstract fun providesKeysPreferencesRepository(keysPreferencesRepositoryDataStoreImpl: KeysPreferencesRepositoryDataStoreImpl): KeysPreferencesRepository

}