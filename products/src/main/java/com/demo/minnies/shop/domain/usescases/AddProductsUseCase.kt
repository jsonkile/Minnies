package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.database.models.Product
import com.demo.minnies.shop.data.repos.ProductsRepo
import javax.inject.Inject

interface AddProductsUseCase {
    suspend operator fun invoke(products: List<Product>): List<Long>
}

class AddProductsUseCaseImpl @Inject constructor(private val productsRepo: ProductsRepo) :
    AddProductsUseCase {
    override suspend fun invoke(products: List<Product>): List<Long> =
        productsRepo.addItems(products)
}