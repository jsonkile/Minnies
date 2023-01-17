package com.demo.minnies.shared.di

import com.demo.minnies.shared.data.repos.*
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCaseImpl
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
    abstract fun bindEncryptor(aesEncryptorImpl: AESEncryptorImpl): Encryptor

    @Binds
    abstract fun bindKeysPreferencesRepository(keysRepositoryHardImpl: KeysRepositoryHardImpl): KeysRepository

    @Binds
    abstract fun bindUserPreferencesRepository(userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl): UserPreferencesRepository

    @Binds
    abstract fun bindsGetUserCurrencyPreferenceUseCase(getUserCurrencyPreferenceUseCaseImpl: GetUserCurrencyPreferenceUseCaseImpl): GetUserCurrencyPreferenceUseCase
}