package com.webtutsplus.ecommerce.dto.checkout

data class CheckoutItemDto (
    val productName: String? = null,
    val quantity:Int = 0,
    val price:Double = 0.0,
    var userId:Int = 0,
    val productId: Long = 0            ) {
}