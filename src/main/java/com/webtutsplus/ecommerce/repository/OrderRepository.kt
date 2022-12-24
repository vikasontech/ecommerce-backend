package com.webtutsplus.ecommerce.repository

import com.webtutsplus.ecommerce.model.Order
import com.webtutsplus.ecommerce.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Int> {
    fun findAllByUserOrderByCreatedDateDesc(user: User): List<Order>
}