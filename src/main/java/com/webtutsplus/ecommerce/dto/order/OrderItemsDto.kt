package com.webtutsplus.ecommerce.dto.order

import javax.validation.constraints.NotNull

data class OrderItemsDto(
    val price: Double,
    val quantity: Int,
    val orderId: Int,
    val productId: Int
)