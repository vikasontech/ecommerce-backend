package com.webtutsplus.ecommerce.dto.cart

data class CartDto(private var cartItems: List<CartItemDto>, var totalCost: Double) {
    fun getcartItems(): List<CartItemDto> {
        return cartItems
    }

    fun setCartItems(cartItemDtoList: List<CartItemDto>) {
        cartItems = cartItemDtoList
    }

    fun setTotalCost(totalCost: Int) {
        this.totalCost = totalCost.toDouble()
    }
}