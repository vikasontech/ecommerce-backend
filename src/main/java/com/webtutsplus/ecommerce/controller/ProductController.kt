package com.webtutsplus.ecommerce.controller

import com.webtutsplus.ecommerce.common.ApiResponse
import com.webtutsplus.ecommerce.dto.product.ProductDto
import com.webtutsplus.ecommerce.service.CategoryService
import com.webtutsplus.ecommerce.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/product")
class ProductController {
    @Autowired
    var productService: ProductService? = null

    @Autowired
    var categoryService: CategoryService? = null

    @get:GetMapping("/")
    val products: ResponseEntity<List<ProductDto>>
        get() {
            val body = productService!!.listProducts()
            return ResponseEntity(body, HttpStatus.OK)
        }

    @PostMapping("/add")
    fun addProduct(@RequestBody productDto: ProductDto): ResponseEntity<ApiResponse> {
        val optionalCategory = categoryService!!.readCategory(productDto.categoryId)
        if (!optionalCategory.isPresent) {
            return ResponseEntity(ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT)
        }
        val category = optionalCategory.get()
        productService!!.addProduct(productDto, category)
        return ResponseEntity(ApiResponse(true, "Product has been added"), HttpStatus.CREATED)
    }

    @PostMapping("/update/{productID}")
    fun updateProduct(
        @PathVariable("productID") productID: Int,
        @RequestBody productDto: @Valid ProductDto
    ): ResponseEntity<ApiResponse> {
        val optionalCategory = categoryService!!.readCategory(
            productDto!!.categoryId
        )
        if (!optionalCategory.isPresent) {
            return ResponseEntity(ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT)
        }
        val category = optionalCategory.get()
        productService!!.updateProduct(productID, productDto, category)
        return ResponseEntity(ApiResponse(true, "Product has been updated"), HttpStatus.OK)
    }
}