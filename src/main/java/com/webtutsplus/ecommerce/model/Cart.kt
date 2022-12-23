package com.webtutsplus.ecommerce.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cart")
data class Cart (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?=0,
    @Column(name = "created_date")
    val createdDate: LocalDateTime,
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    val product: Product,
    @JsonIgnore
    @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User,
    val quantity: Int) {
    constructor(product: Product, quantity: Int, user: User): this(id=0, createdDate=LocalDateTime.now(), product=product, user = user, quantity = quantity)
}
