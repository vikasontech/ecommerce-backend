package com.webtutsplus.ecommerce.dto.cart

import com.webtutsplus.ecommerce.model.Cart
import com.webtutsplus.ecommerce.model.Product
import javax.validation.constraints.NotNull

data class CartItemDto(val id: Int,
    val quantity: @NotNull Int,
    val product: @NotNull Product)  {

    constructor(cart:Cart): this(id=cart.id?:0, quantity=cart.quantity, cart.product)

}