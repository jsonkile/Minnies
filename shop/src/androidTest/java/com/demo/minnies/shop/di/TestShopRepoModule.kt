package com.demo.minnies.shop.di

import com.demo.minnies.shop.data.repos.ShopRepo
import com.demo.minnies.shop.data.repos.ShopRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [ShopRepoModule::class])
abstract class TestShopRepoModule {

    @Binds
    abstract fun bindShopRepo(shopRepoImpl: ShopRepoImpl): ShopRepo

}