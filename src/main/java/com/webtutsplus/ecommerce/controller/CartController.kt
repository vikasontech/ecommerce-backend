package com.webtutsplus.ecommerce.controller

import com.webtutsplus.ecommerce.common.ApiResponse
import com.webtutsplus.ecommerce.dto.cart.AddToCartDto
import com.webtutsplus.ecommerce.dto.cart.CartDto
import com.webtutsplus.ecommerce.exceptions.AuthenticationFailException
import com.webtutsplus.ecommerce.exceptions.CartItemNotExistException
import com.webtutsplus.ecommerce.exceptions.ProductNotExistException
import com.webtutsplus.ecommerce.service.AuthenticationService
import com.webtutsplus.ecommerce.service.CartService
import com.webtutsplus.ecommerce.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException
import javax.validation.Valid

@RestController
@RequestMapping("/cart")
class CartController {
    @Autowired
    private val cartService: CartService? = null

    @Autowired
    private val productService: ProductService? = null

    @Autowired
    private val authenticationService: AuthenticationService? = null
    @PostMapping("/add")
    @Throws(AuthenticationFailException::class, ProductNotExistException::class)
    fun addToCart(
        @RequestBody addToCartDto: AddToCartDto,
        @RequestParam("token") token: String
    ): ResponseEntity<ApiResponse> {
        authenticationService!!.authenticate(token)
        val user = authenticationService.getUser(token)?:throw RuntimeException("User not found")
        val product = productService!!.getProductById(addToCartDto.productId)
        println("product to add" + product.name)
        cartService!!.addToCart(addToCartDto, product, user)
        return ResponseEntity(ApiResponse(true, "Added to cart"), HttpStatus.CREATED)
    }

    @GetMapping("/")
    @Throws(AuthenticationFailException::class)
    fun getCartItems(@RequestParam("token") token: String): ResponseEntity<CartDto> {
        authenticationService!!.authenticate(token)
        val user = authenticationService.getUser(token)
        val cartDto = cartService!!.listCartItems(user)
        return ResponseEntity(cartDto, HttpStatus.OK)
    }

    @PutMapping("/update/{cartItemId}")
    @Throws(AuthenticationFailException::class, ProductNotExistException::class)
    fun updateCartItem(
        @RequestBody cartDto: @Valid AddToCartDto,
        @RequestParam("token") token: String
    ): ResponseEntity<ApiResponse> {
        authenticationService!!.authenticate(token)
        val user = authenticationService.getUser(token)
        val product = productService!!.getProductById(cartDto!!.productId)
        cartService!!.updateCartItem(cartDto, user, product)
        return ResponseEntity(ApiResponse(true, "Product has been updated"), HttpStatus.OK)
    }

    @DeleteMapping("/delete/{cartItemId}")
    @Throws(AuthenticationFailException::class, CartItemNotExistException::class)
    fun deleteCartItem(
        @PathVariable("cartItemId") itemID: Int,
        @RequestParam("token") token: String
    ): ResponseEntity<ApiResponse> {
        authenticationService!!.authenticate(token)
        val userId = authenticationService.getUser(token!!)!!.id!!
        cartService!!.deleteCartItem(itemID, userId)
        return ResponseEntity(ApiResponse(true, "Item has been removed"), HttpStatus.OK)
    }
}