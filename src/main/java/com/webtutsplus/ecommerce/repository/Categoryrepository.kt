package com.webtutsplus.ecommerce.repository

import com.webtutsplus.ecommerce.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface Categoryrepository : JpaRepository<Category?, Int?> {
    fun findByCategoryName(categoryName: String?): Category?
}