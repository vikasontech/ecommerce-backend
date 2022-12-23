package com.webtutsplus.ecommerce

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
object EcommerceApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(EcommerceApplication::class.java, *args)
    }
}