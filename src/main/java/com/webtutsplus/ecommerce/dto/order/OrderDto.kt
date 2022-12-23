package com.webtutsplus.ecommerce.dto.order

import com.webtutsplus.ecommerce.model.Order
import javax.validation.constraints.NotNull

data class OrderDto(
    var id: Int,
    var userId: Int
)