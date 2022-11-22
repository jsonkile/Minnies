package com.demo.minnies.shop.domain.usescases


import com.demo.minnies.shared.utils.CustomExceptions
import com.demo.minnies.shop.data.repos.ShopRepo
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.presentation.models.toView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FetchProductByIdUseCase @Inject constructor(private val repo: ShopRepo) {
    operator fun invoke(id: Int): Flow<ViewProduct> {
        return repo.getProductById(id).onEach { result ->
            if (result == null) {
                throw CustomExceptions.NotFoundException("Sorry, but we couldn't find the product you requested for.")
            }
        }.map { it!!.toView() }
    }
}