package com.demo.minnies.shop.di


import com.demo.minnies.shop.data.repos.CartRepo
import com.demo.minnies.shop.data.repos.CartRepoRoomImpl
import com.demo.minnies.shop.domain.usescases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CartModule {

    @Binds
    abstract fun bindCartRepo(cartRepoRoomImpl: CartRepoRoomImpl): CartRepo

    @Binds
    abstract fun bindFetchCartUseCase(fetchCartUseCaseMockImpl: FetchCartUseCaseMockImpl): FetchCartUseCase

    @Binds
    abstract fun bindAddToCartUseCase(addToCartUseCaseImpl: AddToCartUseCaseImpl): AddToCartUseCase

    @Binds
    abstract fun bindUpdateCartItemUseCase(updateCartItemUseCaseImpl: UpdateCartItemUseCaseImpl): UpdateCartItemUseCase

}