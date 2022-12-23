package com.webtutsplus.ecommerce.dto.product

import com.webtutsplus.ecommerce.model.Product
import javax.validation.constraints.NotNull

data class ProductDto(
    val id: Int,
    val name: String,
    val imageURL: String,
    val price: Double,
    val description: String,
    val categoryId: Int
)
