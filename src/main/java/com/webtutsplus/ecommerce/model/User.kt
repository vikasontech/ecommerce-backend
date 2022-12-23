package com.webtutsplus.ecommerce.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.webtutsplus.ecommerce.enums.Role
import javax.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?=0,

    @Column(name = "first_name")
    val firstName: String,

    @Column(name = "last_name")
    val lastName: String,

    @Column(name = "email")
    val email: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: Role,

    @Column(name = "password")
    val password: String,

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val orders: List<Order>?= emptyList()

)