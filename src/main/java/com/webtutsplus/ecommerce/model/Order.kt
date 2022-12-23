package com.webtutsplus.ecommerce.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "created_date")
    val createdDate: LocalDateTime,

    @Column(name = "total_price")
    val totalPrice: Double,

    @Column(name = "session_id")
    val sessionId: String,

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    val orderItems: List<OrderItem>,

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,
)