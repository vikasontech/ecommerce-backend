package com.webtutsplus.ecommerce.controller

import com.webtutsplus.ecommerce.common.ApiResponse
import com.webtutsplus.ecommerce.dto.product.ProductDto
import com.webtutsplus.ecommerce.model.Product
import com.webtutsplus.ecommerce.model.WishList
import com.webtutsplus.ecommerce.service.AuthenticationService
import com.webtutsplus.ecommerce.service.ProductService
import com.webtutsplus.ecommerce.service.WishListService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/wishlist")
class WishListController {
    @Autowired
    private lateinit var wishListService: WishListService

    @Autowired
    private lateinit var authenticationService: AuthenticationService
    @GetMapping("/{token}")
    fun getWishList(@PathVariable("token") token: String): ResponseEntity<List<ProductDto>> {
        val user_id = authenticationService!!.getUser(token)!!.id!!
        val body = wishListService!!.readWishList(user_id)
        val products: MutableList<ProductDto> = ArrayList()
        for (wishList in body) {
            products.add(ProductService.getDtoFromProduct(wishList.product))
        }
        return ResponseEntity(products, HttpStatus.OK)
    }

    @PostMapping("/add")
    fun addWishList(
        @RequestBody product: Product,
        @RequestParam("token") token: String
    ): ResponseEntity<ApiResponse> {
        authenticationService!!.authenticate(token)
        val user = authenticationService.getUser(token)?:throw RuntimeException("user not found")
        val wishList = WishList(user= user, product = product, createdDate = LocalDateTime.now())
        wishListService!!.createWishlist(wishList)
        return ResponseEntity(ApiResponse(true, "Add to wishlist"), HttpStatus.CREATED)
    }
}