package com.demo.minnies.shop.di

import com.demo.minnies.shop.data.repos.ProductsRepo
import com.demo.minnies.shop.data.repos.ProductsRepoMockImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [ProductsModule::class])
abstract class TestShopRepoModule {

    @Binds
    abstract fun bindShopRepo(shopRepoMockImpl: ProductsRepoMockImpl): ProductsRepo

}