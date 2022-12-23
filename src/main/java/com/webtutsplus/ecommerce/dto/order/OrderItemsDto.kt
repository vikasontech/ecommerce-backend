package com.webtutsplus.ecommerce.dto.order

data class OrderItemsDto(
    val price: Double,
    val quantity: Int,
    val orderId: Int,
    val productId: Int
)