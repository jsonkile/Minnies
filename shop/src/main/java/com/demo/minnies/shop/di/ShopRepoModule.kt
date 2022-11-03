package com.demo.minnies.shop.di

import com.demo.minnies.shop.data.repos.ShopRepo
import com.demo.minnies.shop.data.repos.ShopRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ShopRepoModule {

    @Binds
    abstract fun bindShopRepo(shopRepoImpl: ShopRepoImpl): ShopRepo

}