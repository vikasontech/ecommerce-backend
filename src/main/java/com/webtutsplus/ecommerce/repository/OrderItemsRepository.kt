package com.webtutsplus.ecommerce.repository

import com.webtutsplus.ecommerce.model.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemsRepository : JpaRepository<OrderItem, Int>