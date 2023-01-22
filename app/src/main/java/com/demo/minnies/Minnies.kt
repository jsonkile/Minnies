package com.demo.minnies

import android.app.Application
import com.demo.minnies.shop.domain.usescases.AddProductsUseCase
import com.demo.minnies.shop.util.mockProducts
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltAndroidApp
class Minnies : Application() {
    private val applicationScope = MainScope()

    @Inject
    lateinit var addProductsUseCase: AddProductsUseCase

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            withContext(Dispatchers.IO) {
                addProductsUseCase(mockProducts)
            }
        }
    }
}