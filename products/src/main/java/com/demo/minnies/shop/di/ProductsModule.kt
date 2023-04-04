package com.demo.minnies.shop.di

import com.demo.minnies.shared.data.repos.HelpersRepository
import com.demo.minnies.shared.data.repos.HelpersRepositoryHardImpl
import com.demo.minnies.shop.data.repos.ProductsRepo
import com.demo.minnies.shop.data.repos.ProductsRepoRoomImpl
import com.demo.minnies.shop.domain.usescases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductsModule {
    @Binds
    abstract fun bindAddToCartUseCase(addToCartUseCaseImpl: AddToCartUseCaseImpl): AddToCartUseCase

    @Binds
    abstract fun bindProductsRepo(productsRepoRoomImpl: ProductsRepoRoomImpl): ProductsRepo

    @Binds
    abstract fun bindHelpersRepo(helpersRepositoryHardImpl: HelpersRepositoryHardImpl): HelpersRepository

    @Binds
    abstract fun bindGetAllProductsUseCase(getAllProductsUseCaseImpl: GetAllProductsUseCaseImpl): GetAllProductsUseCase

    @Binds
    abstract fun bindCountAllProductsUseCase(countAllProductsUseCaseImpl: CountAllProductsUseCaseImpl): CountAllProductsUseCase

    @Binds
    abstract fun bindAddProductsUseCase(addProductsUseCaseImpl: AddProductsUseCaseImpl): AddProductsUseCase

    @Binds
    abstract fun bindFetchProductByIdUseCase(fetchProductByIdUseCaseImpl: FetchProductByIdUseCaseImpl): FetchProductByIdUseCase

    @Binds
    abstract fun bindSearchProductsUseCase(searchProductsUseCaseImpl: SearchProductsUseCaseImpl): SearchProductsUseCase

    @Binds
    abstract fun bindFetchFeaturedShopItemsUsedCase(fetchFeaturedShopItemsUseCaseImpl: FetchFeaturedShopItemsUseCaseImpl): FetchFeaturedShopItemsUseCase

    @Binds
    abstract fun bindFetchShopItemsByCategoriesUseCase(fetchShopItemsByCategoriesUseCaseImpl: FetchProductsByCategoriesUseCaseImpl): FetchProductsByCategoriesUseCase
}