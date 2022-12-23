package com.webtutsplus.ecommerce.service

import com.webtutsplus.ecommerce.dto.product.ProductDto
import com.webtutsplus.ecommerce.exceptions.ProductNotExistException
import com.webtutsplus.ecommerce.model.Category
import com.webtutsplus.ecommerce.model.Product
import com.webtutsplus.ecommerce.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService {
    @Autowired
    private val productRepository: ProductRepository? = null
    fun listProducts(): List<ProductDto> {
        val products = productRepository!!.findAll()
        val productDtos: MutableList<ProductDto> = ArrayList()
        for (product in products) {
            val productDto = getDtoFromProduct(product!!)
            productDtos.add(productDto)
        }
        return productDtos
    }

    fun addProduct(productDto: ProductDto, category: Category) {
        val product = getProductFromDto(productDto, category)
        productRepository!!.save(product)
    }

    fun updateProduct(productID: Int, productDto: ProductDto, category: Category) {
        val product = getProductFromDto(productDto, category)
            .copy(id = productID)
        productRepository!!.save(product)
    }

    @Throws(ProductNotExistException::class)
    fun getProductById(productId: Int): Product {
        val optionalProduct = productRepository!!.findById(productId)
        if (!optionalProduct.isPresent) throw ProductNotExistException("Product id is invalid $productId")
        return optionalProduct.get()
    }

    companion object {
        fun getDtoFromProduct(p: Product): ProductDto {
            return ProductDto(
                id = p.id ?: 0,
                name = p.name,
                imageURL = p.imageURL,
                price = p.price,
                description = p.description,
                categoryId = p.category.id
            )
        }

        fun getProductFromDto(
            p: ProductDto,
            c: Category
        ): Product {
            return Product(
                name = p.name,
                imageURL = p.imageURL,
                description = p.description,
                price = p.price,
                category = c
            )
        }
    }
}