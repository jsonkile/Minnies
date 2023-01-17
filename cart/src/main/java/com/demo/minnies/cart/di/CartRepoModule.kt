package com.demo.minnies.cart.di

import com.demo.minnies.cart.data.repos.CartRepo
import com.demo.minnies.cart.data.repos.CartRepoRoomImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CartRepoModule {

    @Binds
    abstract fun bindCartRepo(cartRepoRoomImpl: CartRepoRoomImpl): CartRepo

}