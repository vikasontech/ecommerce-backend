package com.webtutsplus.ecommerce.service

import com.webtutsplus.ecommerce.model.OrderItem
import com.webtutsplus.ecommerce.repository.OrderItemsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class OrderItemsService {
    @Autowired
    private val orderItemsRepository: OrderItemsRepository? = null
    fun addOrderedProducts(orderItem: OrderItem) {
        orderItemsRepository!!.save(orderItem)
    }
}