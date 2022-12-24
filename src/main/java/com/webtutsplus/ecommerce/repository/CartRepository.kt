package com.webtutsplus.ecommerce.repository

import com.webtutsplus.ecommerce.model.Cart
import com.webtutsplus.ecommerce.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Int> {
    fun findAllByUserOrderByCreatedDateDesc(user: User): List<Cart>
    fun deleteByUser(user: User): List<Cart>
}