package com.demo.minnies.shop.di

import com.demo.minnies.shop.domain.usescases.FetchAndGroupShopItemsUseCase
import com.demo.minnies.shop.data.repos.ShopRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideFetchAndGroupShopItemsUseCase(repo: ShopRepo) = FetchAndGroupShopItemsUseCase(repo)
}