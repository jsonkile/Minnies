package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.shop.data.repos.ProductsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CountAllProductsUseCase {
    operator fun invoke(): Flow<Int>
}

class CountAllProductsUseCaseImpl @Inject constructor(private val repo: ProductsRepo) :
    CountAllProductsUseCase {
    override fun invoke() = repo.countAllItems()
}