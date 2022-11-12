package com.demo.minnies.auth.di

import com.demo.minnies.auth.data.repos.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthRepo(authRepoRoomImpl: AuthRepoRoomImpl): AuthRepo

    @Binds
    abstract fun bindUserRepo(userRepoRoomImpl: UserRepoRoomImpl): UserRepo

    @Binds
    abstract fun bindCacheRepo(cacheRepoDataStoreImpl: CacheRepoDataStoreImpl): CacheRepo

}