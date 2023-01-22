package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.database.models.Product
import com.demo.minnies.shop.data.repos.ProductsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllProductsUseCase {
    operator fun invoke(): Flow<List<Product>>
}

class GetAllProductsUseCaseImpl @Inject constructor(private val repo: ProductsRepo) :
    GetAllProductsUseCase {
    override fun invoke() = repo.getAllItems()
}