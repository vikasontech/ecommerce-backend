package com.webtutsplus.ecommerce.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tokens")
data class AuthenticationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = 0,
    val token: String? = UUID.randomUUID().toString(),
    @Column(name = "created_date")
    val createdDate: LocalDateTime? = LocalDateTime.now(),
    @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User
) {

    constructor(user1: com.webtutsplus.ecommerce.model.User) : this(user = user1)

}
