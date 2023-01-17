package com.demo.minnies.shop.di

import com.demo.minnies.shop.data.repos.ProductsRepo
import com.demo.minnies.shop.data.repos.ProductsRepoMockImpl
import com.demo.minnies.shop.domain.usescases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductsModule {

    @Binds
    abstract fun bindProductsRepo(productsRepoMockImpl: ProductsRepoMockImpl): ProductsRepo

    @Binds
    abstract fun bindFetchProductByIdUseCase(fetchProductByIdUseCaseImpl: FetchProductByIdUseCaseImpl): FetchProductByIdUseCase

    @Binds
    abstract fun bindSearchProductsUseCase(searchProductsUseCaseImpl: SearchProductsUseCaseImpl): SearchProductsUseCase

    @Binds
    abstract fun bindFetchFeaturedShopItemsUsedCase(fetchFeaturedShopItemsUseCaseImpl: FetchFeaturedShopItemsUseCaseImpl): FetchFeaturedShopItemsUseCase

    @Binds
    abstract fun bindFetchShopItemsByCategoriesUseCase(fetchShopItemsByCategoriesUseCaseImpl: FetchShopItemsByCategoriesUseCaseImpl): FetchShopItemsByCategoriesUseCase
}