package com.webtutsplus.ecommerce.dto.cart

import javax.validation.constraints.NotNull

data class AddToCartDto(
    val id: Int,
    val productId: @NotNull Int,
    val quantity: @NotNull Int
)