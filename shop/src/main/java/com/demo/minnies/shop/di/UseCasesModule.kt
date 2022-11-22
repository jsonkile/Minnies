package com.demo.minnies.shop.di

import com.demo.minnies.shop.domain.usescases.FetchShopItemsByCategoriesUseCase
import com.demo.minnies.shop.data.repos.ShopRepo
import com.demo.minnies.shop.domain.usescases.FetchFeaturedShopItemsUseCase
import com.demo.minnies.shop.domain.usescases.FetchProductByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideFetchShopItemsByCategoriesUseCase(repo: ShopRepo) =
        FetchShopItemsByCategoriesUseCase(repo)


    @ViewModelScoped
    @Provides
    fun provideFetchFeaturedShopItemsUseCase(repo: ShopRepo) = FetchFeaturedShopItemsUseCase(repo)

    @ViewModelScoped
    @Provides
    fun provideFetchProductByIdUseCase(repo: ShopRepo) = FetchProductByIdUseCase(repo)

}