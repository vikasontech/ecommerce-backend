package com.webtutsplus.ecommerce.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "wishlist")
data class WishList (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?=0,

    @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User,

    @Column(name = "created_date")
    val createdDate: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,

)