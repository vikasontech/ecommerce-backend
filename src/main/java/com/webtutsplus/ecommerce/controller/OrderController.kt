package com.webtutsplus.ecommerce.controller

import com.stripe.exception.StripeException
import com.webtutsplus.ecommerce.common.ApiResponse
import com.webtutsplus.ecommerce.dto.checkout.CheckoutItemDto
import com.webtutsplus.ecommerce.dto.checkout.StripeResponse
import com.webtutsplus.ecommerce.exceptions.AuthenticationFailException
import com.webtutsplus.ecommerce.exceptions.OrderNotFoundException
import com.webtutsplus.ecommerce.model.Order
import com.webtutsplus.ecommerce.service.AuthenticationService
import com.webtutsplus.ecommerce.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
@RequestMapping("/order")
class OrderController {
    @Autowired
    private lateinit var orderService: OrderService

    @Autowired
    private lateinit var authenticationService: AuthenticationService

    // stripe create session API
    @PostMapping("/create-checkout-session")
    @Throws(StripeException::class)
    fun checkoutList(@RequestBody checkoutItemDtoList: List<CheckoutItemDto>): ResponseEntity<StripeResponse> {
        // create the stripe session
        val session = orderService!!.createSession(checkoutItemDtoList)
        val stripeResponse = StripeResponse(session.id)
        // send the stripe session id in response
        return ResponseEntity(stripeResponse, HttpStatus.OK)
    }

    // place order after checkout
    @PostMapping("/add")
    @Throws(AuthenticationFailException::class)
    fun placeOrder(
        @RequestParam("token") token: String,
        @RequestParam("sessionId") sessionId: String
    ): ResponseEntity<ApiResponse> {
        // validate token
        authenticationService!!.authenticate(token)
        // retrieve user
        val user = authenticationService.getUser(token)!!
        // place the order
        orderService!!.placeOrder(user, sessionId)
        return ResponseEntity(ApiResponse(true, "Order has been placed"), HttpStatus.CREATED)
    }

    // get all orders
    @GetMapping("/")
    @Throws(AuthenticationFailException::class)
    fun getAllOrders(@RequestParam("token") token: String): ResponseEntity<List<Order>> {
        // validate token
        authenticationService!!.authenticate(token)
        // retrieve user
        val user = authenticationService.getUser(token)?:throw RuntimeException("User not found")
        // get orders
        val orderDtoList = orderService.listOrders(user)
        return ResponseEntity(orderDtoList, HttpStatus.OK)
    }

    // get orderitems for an order
    @GetMapping("/{id}")
    @Throws(AuthenticationFailException::class)
    fun getOrderById(@PathVariable("id") id: Int, @RequestParam("token") token: String): ResponseEntity<Any?> {
        // validate token
        authenticationService!!.authenticate(token)
        return try {
            val order = orderService!!.getOrder(id)
            ResponseEntity(order, HttpStatus.OK)
        } catch (e: OrderNotFoundException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }
}