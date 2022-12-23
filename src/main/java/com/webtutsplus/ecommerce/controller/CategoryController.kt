package com.webtutsplus.ecommerce.controller

import com.webtutsplus.ecommerce.common.ApiResponse
import com.webtutsplus.ecommerce.model.Category
import com.webtutsplus.ecommerce.service.CategoryService
import com.webtutsplus.ecommerce.utils.Helper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/category")
class CategoryController {

    @Autowired
    private lateinit var categoryService: CategoryService

    @get:GetMapping("/")
    val categories: ResponseEntity<List<Category?>>
        get() {
            val body = categoryService!!.listCategories()
            return ResponseEntity(body, HttpStatus.OK)
        }

    @PostMapping("/create")
    fun createCategory(@RequestBody category: @Valid Category?): ResponseEntity<ApiResponse> {
        if (Helper.notNull(categoryService!!.readCategory(category!!.categoryName))) {
            return ResponseEntity(ApiResponse(false, "category already exists"), HttpStatus.CONFLICT)
        }
        categoryService.createCategory(category)
        return ResponseEntity(ApiResponse(true, "created the category"), HttpStatus.CREATED)
    }

    @PostMapping("/update/{categoryID}")
    fun updateCategory(
        @PathVariable("categoryID") categoryID: Int,
        @RequestBody category: @Valid Category
    ): ResponseEntity<ApiResponse> {
        // Check to see if the category exists.
        if (Helper.notNull(categoryService!!.readCategory(categoryID))) {
            // If the category exists then update it.
            categoryService.updateCategory(categoryID, category)
            return ResponseEntity(ApiResponse(true, "updated the category"), HttpStatus.OK)
        }

        // If the category doesn't exist then return a response of unsuccessful.
        return ResponseEntity(ApiResponse(false, "category does not exist"), HttpStatus.NOT_FOUND)
    }
}