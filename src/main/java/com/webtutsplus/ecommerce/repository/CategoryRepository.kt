package com.webtutsplus.ecommerce.repository

import com.webtutsplus.ecommerce.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Int> {
    fun findByCategoryName(categoryName: String): Category
}