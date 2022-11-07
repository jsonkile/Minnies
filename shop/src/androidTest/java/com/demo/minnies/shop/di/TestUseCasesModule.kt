package com.demo.minnies.shop.di

import com.demo.minnies.shop.data.repos.ShopRepo
import com.demo.minnies.shop.data.repos.ShopRepoMockImpl
import com.demo.minnies.shop.data.repos.ShopRepoRoomImpl
import com.demo.minnies.shop.domain.usescases.FetchFeaturedShopItemsUseCase
import com.demo.minnies.shop.domain.usescases.FetchShopItemsByCategoriesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [UseCasesModule::class])
object TestUseCasesModule {

    @Provides
    fun provideFetchShopItemsByCategoriesUseCase(repo: ShopRepo) =
        FetchShopItemsByCategoriesUseCase(repo)


    @Provides
    fun provideFetchFeaturedShopItemsUseCase(repo: ShopRepo) = FetchFeaturedShopItemsUseCase(repo)

}