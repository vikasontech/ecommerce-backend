package com.webtutsplus.ecommerce.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.webtutsplus.ecommerce.dto.product.ProductDto
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "products")
data class Product (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?=0,
    val name: @NotNull String,
    val imageURL: @NotNull String,
    val price: @NotNull Double,
    val description: @NotNull String,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private val wishListList: List<WishList>?= emptyList(),

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private val carts: List<Cart>?= emptyList(),

    )