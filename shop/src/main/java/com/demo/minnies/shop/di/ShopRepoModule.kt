package com.demo.minnies.shop.di

import com.demo.minnies.shop.repos.ShopRepo
import com.demo.minnies.shop.repos.ShopRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ShopRepoModule {

    @Binds
    abstract fun bindShopRepo(shopRepoImpl: ShopRepoImpl): ShopRepo

}