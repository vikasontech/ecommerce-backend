package com.webtutsplus.ecommerce.repository

import com.webtutsplus.ecommerce.model.WishList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WishListRepository : JpaRepository<WishList?, Int?> {
    fun findAllByUserIdOrderByCreatedDateDesc(userId: Int): List<WishList>
}