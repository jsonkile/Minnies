package com.demo.minnies.auth.di

import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.auth.domain.GetCachedUserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideGetCachedUserUseCase(cacheRepo: CacheRepo) = GetCachedUserUseCaseImpl(cacheRepo)
}