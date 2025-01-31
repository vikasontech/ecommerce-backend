package com.webtutsplus.ecommerce.repository

import com.webtutsplus.ecommerce.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ProductRepository : JpaRepository<Product, Int>