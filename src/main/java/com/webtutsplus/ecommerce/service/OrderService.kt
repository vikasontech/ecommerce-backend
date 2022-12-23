package com.webtutsplus.ecommerce.service

import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.checkout.Session
import com.stripe.param.checkout.SessionCreateParams
import com.webtutsplus.ecommerce.dto.checkout.CheckoutItemDto
import com.webtutsplus.ecommerce.exceptions.OrderNotFoundException
import com.webtutsplus.ecommerce.model.Order
import com.webtutsplus.ecommerce.model.User
import com.webtutsplus.ecommerce.repository.OrderItemsRepository
import com.webtutsplus.ecommerce.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class OrderService {
    @Autowired
    private lateinit var cartService: CartService

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var orderItemsRepository: OrderItemsRepository

    @Value("\${BASE_URL}")
    private val baseURL: String? = null

    @Value("\${STRIPE_SECRET_KEY}")
    private val apiKey: String? = null

    // create total price
    fun createPriceData(checkoutItemDto: CheckoutItemDto): SessionCreateParams.LineItem.PriceData {
        return SessionCreateParams.LineItem.PriceData.builder()
            .setCurrency("usd")
            .setUnitAmount((checkoutItemDto.price * 100).toLong())
            .setProductData(
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(checkoutItemDto.productName)
                    .build()
            )
            .build()
    }

    // build each product in the stripe checkout page
    fun createSessionLineItem(checkoutItemDto: CheckoutItemDto): SessionCreateParams.LineItem {
        return SessionCreateParams.LineItem.builder() // set price for each product
            .setPriceData(createPriceData(checkoutItemDto)) // set quantity for each product
            .setQuantity(checkoutItemDto.quantity.toString().toLong())
            .build()
    }

    // create session from list of checkout items
    @Throws(StripeException::class)
    fun createSession(checkoutItemDtoList: List<CheckoutItemDto>): Session {

        // supply success and failure url for stripe
        val successURL = baseURL + "payment/success"
        val failedURL = baseURL + "payment/failed"

        // set the private key
        Stripe.apiKey = apiKey
        val sessionItemsList: MutableList<SessionCreateParams.LineItem> = ArrayList()

        // for each product compute SessionCreateParams.LineItem
        for (checkoutItemDto in checkoutItemDtoList) {
            sessionItemsList.add(createSessionLineItem(checkoutItemDto))
        }

        // build the session param
        val params = SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setCancelUrl(failedURL)
            .addAllLineItem(sessionItemsList)
            .setSuccessUrl(successURL)
            .build()
        return Session.create(params)
    }

    fun placeOrder(user: User, sessionId: String) {
        // first let get cart items for the user
        //TODO: NEED TO IMPLEMENT

//        val cartDto = cartService!!.listCartItems(user)
//        val cartItemDtoList = cartDto!!.getcartItems()
//
//        // create the order and save it
//        val newOrder = Order(
//            createdDate = LocalDateTime.now(),
//            sessionId = sessionId,
//            user = user,
//            totalPrice = cartDto.totalCost,
//        )
//        newOrder.setCreatedDate(Date())
//        newOrder.setSessionId(sessionId)
//        newOrder.setUser(user)
//        newOrder.setTotalPrice(cartDto.totalCost)
//        orderRepository!!.save(newOrder)
//        for ((_, quantity, product) in cartItemDtoList) {
//            // create orderItem and save each one
//            val orderItem = OrderItem()
//            orderItem.setCreatedDate(Date())
//            orderItem.setPrice(product!!.price)
//            orderItem.setProduct(product)
//            orderItem.setQuantity(quantity)
//            orderItem.setOrder(newOrder)
//            // add to order item list
//            orderItemsRepository!!.save(orderItem)
//        }
//        //
//        cartService.deleteUserCartItems(user)
    }

    fun listOrders(user: User): List<Order> {
        return orderRepository!!.findAllByUserOrderByCreatedDateDesc(user)
    }

    @Throws(OrderNotFoundException::class)
    fun getOrder(orderId: Int): Order {
        val order = orderRepository!!.findById(orderId)
        if (order.isPresent) {
            return order.get()
        }
        throw OrderNotFoundException("Order not found")
    }
}