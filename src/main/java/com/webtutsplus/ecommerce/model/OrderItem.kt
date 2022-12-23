package com.webtutsplus.ecommerce.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "orderitems")
data class OrderItem (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int,

    @Column(name = "quantity")
    val quantity: @NotNull Int,

    @Column(name = "price")
    val price: @NotNull Double,

    @Column(name = "created_date")
    val createdDate: Date,

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    val order: Order,

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    val product: Product,
)