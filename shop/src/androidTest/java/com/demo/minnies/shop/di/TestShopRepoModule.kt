package com.demo.minnies.shop.di

import com.demo.minnies.shop.data.repos.ShopRepo
import com.demo.minnies.shop.data.repos.ShopRepoMockImpl
import com.demo.minnies.shop.data.repos.ShopRepoRoomImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [ShopRepoModule::class])
abstract class TestShopRepoModule {

    @Binds
    abstract fun bindShopRepo(shopRepoMockImpl: ShopRepoMockImpl): ShopRepo

}