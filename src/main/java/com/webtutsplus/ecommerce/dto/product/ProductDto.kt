package com.webtutsplus.ecommerce.dto.product

data class ProductDto(
    val id: Int,
    val name: String,
    val imageURL: String,
    val price: Double,
    val description: String,
    val categoryId: Int
)
