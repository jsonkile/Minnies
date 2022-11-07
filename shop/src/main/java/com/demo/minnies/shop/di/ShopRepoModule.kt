package com.demo.minnies.shop.di

import com.demo.minnies.shop.data.repos.ShopRepo
import com.demo.minnies.shop.data.repos.ShopRepoMockImpl
import com.demo.minnies.shop.data.repos.ShopRepoRoomImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ShopRepoModule {

    @Binds
    abstract fun bindShopRepo(shopRepoMockImpl: ShopRepoMockImpl): ShopRepo

}