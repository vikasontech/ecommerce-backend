package com.webtutsplus.ecommerce.service

import com.webtutsplus.ecommerce.dto.cart.AddToCartDto
import com.webtutsplus.ecommerce.dto.cart.CartDto
import com.webtutsplus.ecommerce.dto.cart.CartItemDto
import com.webtutsplus.ecommerce.exceptions.CartItemNotExistException
import com.webtutsplus.ecommerce.model.Cart
import com.webtutsplus.ecommerce.model.Product
import com.webtutsplus.ecommerce.model.User
import com.webtutsplus.ecommerce.repository.CartRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class CartService {
    @Autowired
    private var cartRepository: CartRepository? = null

    constructor() {}
    constructor(cartRepository: CartRepository?) {
        this.cartRepository = cartRepository
    }

    fun addToCart(addToCartDto: AddToCartDto, product: Product, user: User) {
        val cart = Cart(product=product,
            quantity = addToCartDto.quantity,
            user = user)
        cartRepository!!.save(cart)
    }

    fun listCartItems(user: User?): CartDto {
        val cartList = cartRepository!!.findAllByUserOrderByCreatedDateDesc(user)
        val cartItems: MutableList<CartItemDto> = ArrayList()
        for (cart in cartList!!) {
            val cartItemDto = getDtoFromCart(cart!!)
            cartItems.add(cartItemDto)
        }
        var totalCost = 0.0
        for ((_, quantity, product) in cartItems) {
            totalCost += product!!.price * quantity!!
        }
        return CartDto(cartItems, totalCost)
    }

    fun updateCartItem(cartDto: AddToCartDto, user: User?, product: Product?) {
        val cart = cartRepository!!.getOne(cartDto.id)
            ?.copy(
                quantity = cartDto.quantity,
                createdDate = LocalDateTime.now()
            )
        cartRepository!!.save(cart)
    }

    @Throws(CartItemNotExistException::class)
    fun deleteCartItem(id: Int, userId: Int) {
        if (!cartRepository!!.existsById(id)) throw CartItemNotExistException("Cart id is invalid : $id")
        cartRepository!!.deleteById(id)
    }

    fun deleteCartItems(userId: Int) {
        cartRepository!!.deleteAll()
    }

    fun deleteUserCartItems(user: User?) {
        cartRepository!!.deleteByUser(user)
    }

    companion object {
        fun getDtoFromCart(cart: Cart): CartItemDto {
            return CartItemDto(cart)
        }
    }
}