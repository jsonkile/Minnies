package com.demo.minnies.auth.di

import com.demo.minnies.auth.data.repos.*
import com.demo.minnies.auth.domain.*
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
    abstract fun bindGetCachedUserUseCase(getCachedUserUseCaseImpl: GetCachedUserUseCaseImpl): GetCachedUserUseCase

    @Binds
    abstract fun bindCacheRepo(cacheRepoDataStoreImpl: CacheRepoDataStoreImpl): CacheRepo

    @Binds
    abstract fun bindGetUserUseCase(getUserUseCaseImpl: GetUserUseCaseImpl): GetUserUseCase

    @Binds
    abstract fun bindLoginUserUseCase(loginUserUseCaseImpl: LoginUserUseCaseImpl): LoginUserUseCase

    @Binds
    abstract fun bindRegisterUserUseCase(registerUserUseCaseImpl: RegisterUserUseCaseImpl): RegisterUserUseCase

    @Binds
    abstract fun bindUpdateUserUseCase(updateUserUseCaseImpl: UpdateUserShippingAddressUseCaseImpl): UpdateUserShippingAddressUseCase

    @Binds
    abstract fun bindUpdateCachedUserUseCase(updateCachedUserUseCaseImpl: UpdateCachedUserUseCaseImpl): UpdateCachedUserUseCase
}