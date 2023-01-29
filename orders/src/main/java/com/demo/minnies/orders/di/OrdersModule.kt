package com.demo.minnies.orders.di

import com.demo.minnies.orders.data.OrdersRepo
import com.demo.minnies.orders.data.OrdersRepoRoomImpl
import com.demo.minnies.orders.domain.FetchUserOrdersUseCase
import com.demo.minnies.orders.domain.FetchUserOrdersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class OrdersModule {

    @Binds
    abstract fun bindOrdersRepo(ordersRepoRoomImpl: OrdersRepoRoomImpl): OrdersRepo

    @Binds
    abstract fun bindFetchUserOrdersUseCase(fetchUserOrdersUseCaseImpl: FetchUserOrdersUseCaseImpl): FetchUserOrdersUseCase
}