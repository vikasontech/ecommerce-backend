package com.webtutsplus.ecommerce.service

import com.webtutsplus.ecommerce.model.Category
import com.webtutsplus.ecommerce.repository.Categoryrepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class CategoryService(private val categoryrepository: Categoryrepository) {
    fun listCategories(): List<Category?> {
        return categoryrepository.findAll().toList()
    }

    fun createCategory(category: Category) {
        categoryrepository.save(category)
    }

    fun readCategory(categoryName: String?): Category? {
        return categoryrepository.findByCategoryName(categoryName)
    }

    fun readCategory(categoryId: Int): Optional<Category?> {
        return categoryrepository.findById(categoryId)
    }

    fun updateCategory(categoryID: Int, newCategory: Category) {
        val category = categoryrepository.findById(categoryID).get()
            .copy(
                categoryName = newCategory.categoryName,
                description = newCategory.description,
                products = newCategory.products,
                imageUrl = newCategory.imageUrl
            )
        categoryrepository.save(category)
    }
}