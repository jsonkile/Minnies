package com.demo.minnies.cart.di

import com.demo.minnies.cart.data.repos.CartRepo
import com.demo.minnies.cart.data.repos.CartRepoRoomImpl
import com.demo.minnies.cart.domain.usecases.*
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
    abstract fun bindFetchCartUseCase(fetchCartUseCaseRoomImpl: FetchCartUseCaseRoomImpl): FetchCartUseCase

    @Binds
    abstract fun bindUpdateCartItemUseCase(updateCartItemUseCaseImpl: UpdateCartItemUseCaseImpl): UpdateCartItemUseCase

    @Binds
    abstract fun bindCheckoutCartUseCase(checkoutCartUseCaseImpl: CheckoutCartUseCaseImpl): CheckoutCartUseCase

    @Binds
    abstract fun bindDeleteCartItemUseCase(deleteCartUseCaseRoomImpl: DeleteCartItemUseCaseImpl): DeleteCartItemUseCase
}
